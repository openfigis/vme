package org.vme.web.service;

import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * curl -X PUT http://127.0.0.1:9090/orders-server/orders/1?customer_name=bob 
 * curl -X GET http://127.0.0.1:9090/orders-server/orders/1 
 * curl -X GET http://127.0.0.1:9090/orders-server/orders/list
 */

@Path("orders")
@RequestScoped
public class OrdersService
{
   public static Map<String, String> orders = new TreeMap<String, String>();

   @Path("/{order}")
   @PUT
   @Produces("text/html")
   public String create(@PathParam("order") String order, @QueryParam("customer_name") String customerName)
   {
      orders.put(order, customerName);
      return "Added order #" + order;
   }

   @Path("/{order}")
   @GET
   @Produces("text/html")
   public String find(@PathParam("order") String order)
   {
      if (orders.containsKey(order))
         return "<h2>Details on Order #" + order + "</h2><p>Customer name: " + orders.get(order);

      throw new WebApplicationException(Response.Status.NOT_FOUND);
   }

   @Path("/list")
   @GET
   @Produces("text/html")
   public String list()
   {
      String header = "<h2>All Orders</h2>\n";

      header += "<ul>";
      for (Map.Entry<String, String> order : orders.entrySet())
         header += "\n<li>#" + order.getKey() + " for " + order.getValue() + "</li>";

      header += "\n</ul>";

      return header;
   }
}