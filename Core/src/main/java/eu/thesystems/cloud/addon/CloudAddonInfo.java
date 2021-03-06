package eu.thesystems.cloud.addon;
/*
 * Created by derrop on 16.11.2019
 */

import eu.thesystems.cloud.addon.dependency.MavenDependency;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CloudAddonInfo {

    private String name;
    private String version;
    private String main;
    private String[] authors;
    private Collection<MavenDependency> dependencies;
    private transient URL url;
    private transient URLClassLoader classLoader;

    public void setUrl(URL url) {
        if (this.url != null) {
            throw new IllegalStateException();
        }
        this.url = url;
    }

    public void setClassLoader(URLClassLoader classLoader) {
        if (this.classLoader != null) {
            throw new IllegalStateException();
        }
        this.classLoader = classLoader;
    }
}
