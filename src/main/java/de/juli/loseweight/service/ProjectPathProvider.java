package de.juli.loseweight.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Bietet Methoden zum einfachen Zugriff auf verschiedene Datei-Pfade der
 * Projektmodelle.<br/>
 * <br>
 * 
 * @author ulrichkloodt
 */
@Component
public class ProjectPathProvider implements Serializable {
	private static final long serialVersionUID = 1L;
	private static ProjectPathProvider provider = new ProjectPathProvider();

	private Path path;

	/**
	 * Privater default Konstruktor, um das Bildern von weiteren Instanzen zu
	 * verhindern.
	 */
	private ProjectPathProvider() {
	}

	/**
	 * Setzt das Root - Verzeichnis des Projektes, aus dem heraus diese Methode
	 * aufgerufen wurde und gibt den instanziierten {@link ProjectPathProvider}
	 * zurück.
	 * 
	 * @param arr ein Array vom Type {@link java.lang.String}
	 * @return {@link de.nrw.lanuv.idvs.tools.util.ProjectPathProvider} - Instanz
	 */
	public static ProjectPathProvider appPath(String start, String... arr) {
		provider.path = Paths.get(start, arr);
		return provider;
	}

	/**
	 * Setzt das Root - Verzeichnis des Projektes, aus dem heraus diese Methode
	 * aufgerufen wurde und gibt den instanziierten {@link ProjectPathProvider}
	 * zurück.
	 * 
	 * @return {@link de.nrw.lanuv.idvs.tools.util.ProjectPathProvider} - Instanz
	 */
	public static ProjectPathProvider root() {
		provider.path = classPath().getPath().getParent().getParent();
		return provider;
	}

	/**
	 * Setzt das Root - Verzeichnis des Projektes, mit den als Array übergebenen
	 * Unterverzeichnissen aus dem heraus diese Methode aufgerufen wurde und gibt
	 * den instanziierten {@link ProjectPathProvider} zurück.
	 * 
	 * @param arr ein Array vom Type {@link java.lang.String}
	 * @return {@link de.nrw.lanuv.idvs.tools.util.ProjectPathProvider} - Instanz
	 */
	public static ProjectPathProvider root(String... arr) {
		provider.path = Paths.get(root().getPathAsString(), arr);
		return provider;
	}

	/**
	 * Setzt das Root - Verzeichnis des Projektes, für die Klasse, die der Methode
	 * übergeben wurde und gibt den instanziierten mit den als Array übergebenen
	 * Unterverzeichnissen {@link ProjectPathProvider} zurück.
	 * 
	 * @return {@link de.nrw.lanuv.idvs.tools.util.ProjectPathProvider} - Instanz
	 */
	public static ProjectPathProvider classPath() {
		String file = ProjectPathProvider.class.getResource("/").getFile();
		provider.path = Paths.get(new File(file).toString());
		return provider;
	}

	/**
	 * Setzt das Class - Path - Verzeichnis des Projektes mit den als Array
	 * übergebenen Unterverzeichnissen und gibt den instanziierten
	 * {@link ProjectPathProvider} zurück.
	 * 
	 * @param arr ein Array vom Type {@link java.lang.String}
	 * @return {@link de.nrw.lanuv.idvs.tools.util.ProjectPathProvider} - Instanz
	 */
	public static ProjectPathProvider classPath(String... arr) {
		String file = ProjectPathProvider.class.getResource("/").getFile();
		provider.path = Paths.get(new File(file).toString(), arr);
		return provider;
	}

	/**
	 * 
	 * Setzt einen Pfad entsprechend der Werte im {@link java.lang.String} - Array
	 * vom Klassenpfad ausgehend, er über der den
	 * {@link org.springframework.core.io.ResourceLoader} geladen wird.<br />
	 * <br />
	 * <b>Hint</b>: Diese Methode bietet sich an, wenn in Testklassen Ressourcen aus
	 * dem "src/main/resources" - Verzeichnis in den Klassenpfad gewandet sind.
	 * 
	 * @param loader {@link org.springframework.core.io.ResourceLoader}
	 * @param arr    ein Array vom Type {@link java.lang.String}
	 * @return {@link de.nrw.lanuv.idvs.tools.util.ProjectPathProvider} - Instanz
	 * @throws IOException
	 */
	public static ProjectPathProvider classPath(ResourceLoader loader, String... arr) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("classpath:");
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i < arr.length) {
				sb.append("/");
			}
		}
		provider.path = Paths.get(loader.getResource(sb.toString()).getURI());
		return provider;
	}

	/**
	 * Liefert den gesetzten Pfad als {@link java.nio.file.Path}
	 * 
	 * @return provider.path
	 */
	public Path getPath() {
		return provider.path;
	}

	/**
	 * @return aktuellen Pfad als {@link java.io.File}
	 */
	public File getPathAsFile() {
		return provider.path.toFile();
	}

	/**
	 * @return aktuellen Pfad als {@link java.lang.String}
	 */
	public String getPathAsString() {
		return provider.path.toString();
	}
}