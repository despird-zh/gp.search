package com.gp.search;

import com.gp.util.ConfigSettingUtils;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Factory bean that creates an embedded ElasticSearch node
 *
 */
@Component
public class ElasticSearchNodeFactoryBean implements FactoryBean<Node>,DisposableBean {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Node node;

    @Override
    public Node getObject() throws Exception {
        return getNode();
    }

    @Override
    public Class getObjectType() {
        return Node.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private Node getNode() {

        LOGGER.info("Initializing ElasticSearch node ");
        Settings.Builder settingsBuilder =
                Settings.settingsBuilder();

        settingsBuilder.put("node.name", ConfigSettingUtils.getSystemOption("es.node.name"));
        settingsBuilder.put("path.data", ConfigSettingUtils.getSystemOption("es.path.data"));
        settingsBuilder.put("http.enabled", false);

        Settings settings = settingsBuilder.build();

        node = NodeBuilder.nodeBuilder()
                .settings(settings)
                .clusterName(ConfigSettingUtils.getSystemOption("es.cluster.name"))
                .data(true).local(true).node();

        return node;
    }

    @Override
    public void destroy() throws Exception {

        try {
            LOGGER.info("Closing ElasticSearch node " + node.settings().get("name") );
            node.close();
        } catch (final Exception e) {
            LOGGER.error("Error closing Elasticsearch node: ", e);
        }
    }
}
