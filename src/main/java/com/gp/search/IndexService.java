package com.gp.search;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {

    // Our embedded ElasticSearch Node
    private Node node;

    // Injecting the embedded ElasticSearch Node
    @Autowired
    IndexService(Node node) {
        this.node = node;
    }

    public SearchResponse getAll() {

        // we retrieve the client from the node
        Client client = this.node.client();

        // we execute our search query using the client
        return client.prepareSearch("index_name")
                .setQuery(QueryBuilders.matchAllQuery())
                .execute()
                .actionGet();
    }

}
