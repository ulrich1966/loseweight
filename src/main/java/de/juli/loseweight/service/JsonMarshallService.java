package de.juli.loseweight.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import de.juli.loseweight.exeption.ModelCreateException;


/**
 * Schreibt oder liest Json Daten in/aus eine(r) Datei<br>
 * <br>
 * 
 * @author ulrichkloodt
 *
 * @param <T>
 */
@Service
public class JsonMarshallService<T> {
	private static final Logger LOG = LoggerFactory.getLogger(JsonMarshallService.class);
	private int count;

	/**
	 * Übernimmt ein {@link org.json.JSONArray} das im JSON-Format in eine Datei
	 * geschrieben werden soll. Löscht eine ggf. bereits vorhandene Datei und
	 * erstellt eine Neue des angegeben Namens.
	 * 
	 * @param jsons  {@link org.json.JSONArray} - Objekt mit Daten
	 * @param target {@link java.nio.file.Path} Datei in die geschrieben werden soll
	 * @return {@link java.io.File} Datei in die geschrieben worden ist.
	 * @throws IOException
	 */
	public File marshall(JSONArray jsons, Path target) throws IOException, ModelCreateException {
		
		if (target.getFileName().endsWith(".json")) {
			throw new ModelCreateException(String.format("Die Zieldatei [ %s ] hat nicht die .json Extention", target));
		}
		
		if (target.toFile().exists())
			Files.delete(target);
		
		Files.write(target, jsons.toString().getBytes(), StandardOpenOption.CREATE);
		return target.toFile();
	}

	/**
	 * Übernimmt ein {@link org.json.JSONObject} das im JSON-Format in eine Datei
	 * geschrieben werden soll. Löscht eine ggf. bereits vorhandene Datei und
	 * erstellt eine Neue des angegeben Namens.
	 * 
	 * @param jsons  {@link org.json.JSONArray} - Objekt mit Daten
	 * @param target {@link java.nio.file.Path} Datei in die geschrieben werden soll
	 * @return {@link java.io.File} Datei in die geschrieben worden ist.
	 * @throws IOException
	 */
	public File marshall(JSONObject json, Path target) throws IOException, ModelCreateException {

		if (target.getFileName().endsWith(".json")) {
			throw new ModelCreateException(String.format("Die Zieldatei [ %s ] hat nicht die .json Extention", target));
		}

		if (target.toFile().exists())
			Files.delete(target);

		Files.write(target, json.toString().getBytes(), StandardOpenOption.CREATE);
		return target.toFile();
	}

	/**
	 * Liest aus einer JSON-Datei JSON-Objekte und erzeugt eine Liste des
	 * Kassen-Typs T dieser Instanz
	 * 
	 * @param clazz  {@link java.lang.Class} der Objekt-Typ aus dem die Liste
	 *               bestehen soll
	 * @param source {@link java.nio.file.Path} der Speicherort der auszulesenden
	 *               Datei
	 * @return {@link java.util.List} die Liste mit den eingelesenen Objekten
	 * @throws IOException
	 */
	@SuppressWarnings("hiding")
	public <T> List<T> unMarshall(Class<T> clazz, Path source) throws IOException, ModelCreateException {

		if (source.getFileName().endsWith(".json")) {
			throw new ModelCreateException(String.format("Die Quelldatei [ %s ] hat nicht die .json", source));
		}

		String jsonString = new String(Files.readAllBytes(source));
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
		TypeFactory typeFactory = om.getTypeFactory();
		List<T> resultList = om.readValue(jsonString, typeFactory.constructCollectionType(List.class, clazz));
		this.count = resultList.size();
		return resultList;
	}

	/**
	 * Konsumiert ein {@link java.sql.ResultSet} - Objekt und eine Zielklasse,
	 * überläd die {@link JsonMarshallService#unMarshall} Methode und liefert aus
	 * den als {@link org.json.JSONArray} gewonnenen Daten eine
	 * {@link java.util.List} des angeforderten Typs zurück.<br>
	 * <br>
	 * 
	 * @param <T>
	 * @param clazz
	 * @param resultSet
	 * @return
	 * @throws IOException
	 * @throws ModelCreateException
	 * @throws JSONException
	 * @throws SQLException
	 */
	@SuppressWarnings("hiding")
	public <T> List<T> unMarshall(Class<T> clazz, ResultSet resultSet) throws IOException, ModelCreateException, JSONException, SQLException {
		JSONArray jsonArray = unMarshall(resultSet);
		List<JSONObject> jsonList = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			jsonList.add(object);
		}

		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		TypeFactory typeFactory = om.getTypeFactory();
		return om.readValue(jsonArray.toString(), typeFactory.constructCollectionType(List.class, clazz));
	}

	/**
	 * Konsumiert ein {@link java.sql.ResultSet} - Objekt, extrahiert die
	 * {@link java.sql.ResultSetMetaData} zum Erhalt der Spaltennamen, setzt den
	 * Datensatz-Zeiger auf 0 und iteriert über das Result-Set um einem
	 * {@link org.json.JSONObject} Daten-Typ und Feld-Namen der jeweiligen Spalte
	 * hinzuzufügen. Sammelt die erzeugten JSON-Objekte in einem
	 * {@link org.json.JSONArray}, das zurückgegeben wird.
	 * 
	 * @param resultSet {@link java.sql.ResultSet} über das iteriert werden soll
	 * @return Das generierte {@link org.json.JSONArray} - Objekt
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray unMarshall(ResultSet resultSet) throws SQLException, JSONException {
		JSONArray jsons = new JSONArray();
		ResultSetMetaData rsmd = resultSet.getMetaData();

		// resultSet.first();
		this.count = 0;
		while (resultSet.next()) {
			int numColumns = rsmd.getColumnCount();
			JSONObject json = new JSONObject();

			for (int i = 1; i <= numColumns; i++) {
				String columnName = rsmd.getColumnName(i);
				if (null == resultSet.getObject(columnName)) {
					json.put(columnName, "null");
				} else {
					json.put(columnName, resultSet.getObject(columnName));
				}
			}
			this.count++;
			jsons.put(json);
		}
		return jsons;
	}

	/**
	 * Kosumiert einen Pfad zu einer JSON - Datei und generiet aus deren Einterägen
	 * ein {@link org.json.JSONArray}<br>
	 * <br>
	 * 
	 * @param jsonFile
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public JSONArray unMarshall(Path jsonFile) throws StreamReadException, DatabindException, IOException {
		ObjectMapper om = new ObjectMapper();
		JSONObject values = om.readValue(jsonFile.toFile(), JSONObject.class);
		JSONArray jsonArray = new JSONArray(values);

		for (int i = 0; i < jsonArray.length(); i++) {
			LOG.debug("{}", jsonArray.get(i));
		}

		return jsonArray;
	}

	/**
	 * Übernimmt ein Objket und erzugt daraus ein {@link org.json.JSONArray}<br>
	 * <br>
	 * 
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public String unMarshall(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}
}
