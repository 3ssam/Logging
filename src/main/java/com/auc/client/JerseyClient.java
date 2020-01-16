package com.auc.client;

import com.aas.auc.cache.util.CacheUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClient {

	public static void main(String[] args) {
		update("352");
	}

	public static void update(String id) {

		try {

			CacheUtil.loadAll("jdbc/AUCCP");

			Client client = Client.create();

			WebResource webResource = client
					.resource(CacheUtil.getCustomProperties().get("AUC_COMMENT_MANAEGMENT_SERVICE_URL"))
					.path("comments").path(id);

			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
