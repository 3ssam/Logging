package com.auc.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;

import com.auc.dao.CommentDAO;
import com.auc.dao.CommentsDAOImpl;
import com.auc.model.Comment;
import com.auc.response.CommentResponse;
import com.auc.utility.ResponseWriterUtility;

@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("/comments")
public class CommentService {

	private CommentDAO dao = new CommentsDAOImpl();
	private final static Logger _LOGGER = Logger.getLogger(CommentService.class);
	

	@GET
	@Path("/{recordId}")
	public Response getComments(@PathParam("recordId") String record_Id, @QueryParam("start") String start,
			@QueryParam("end") String end) {
		int recordId;
		int resultCount = -1;
		JSONObject array = null;
		List<Comment> comments = new ArrayList<>();
		try {
			recordId = Integer.parseInt(record_Id);
			_LOGGER.info("record_Id is " + recordId);
			_LOGGER.info("start is " + start);
			_LOGGER.info("end is " + end);
			if (recordId == -1) {
				comments = dao.getPendingComments(Integer.parseInt(start), Integer.parseInt(end));
				resultCount = dao.countPendingComments();
			} else {
				comments = dao.getApprovedComments(recordId);
				resultCount = dao.countPendingComments();
			}
		} catch (SQLException | NumberFormatException e) {
			_LOGGER.error("Error is ", e);
//			_LOGGER.error(e.getStackTrace().toString());
//			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("Error Happen while connect with database")
					.build();
		}
		_LOGGER.info("Succesful Request");
		_LOGGER.info("resultCount is ===> " + resultCount);
		array = ResponseWriterUtility.WriteResponse(comments, resultCount);
		_LOGGER.info("data is ===> " + array.toString());
		return Response.status(Response.Status.OK).entity(array.toString()).build();
	}

	@POST
	@Path("/")
	public Response addComment(Comment comment) {
		// Comment comment = null;
		try {
			// comment = dao.getCommentById(345);
			if (!dao.addComment(comment)) {
				_LOGGER.error("Error Happen During Adding");
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Error Happen During Adding").build();
			}
		} catch (SQLException e) {
//			_LOGGER.error("Error is " , e);
//			_LOGGER.error(e.getStackTrace().toString());
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("Error Happen while connect with database")
					.build();
		}
		_LOGGER.info("Succesful Request");
		_LOGGER.info(comment.toString());
		return Response.status(Response.Status.OK).entity("Succesful Request").build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteComment(@PathParam("id") String id) {
		int commentId;
		Comment comment = null;
		try {
			_LOGGER.info("id is " + id);
			commentId = Integer.parseInt(id);
			comment = dao.deleteCommentById(commentId);
			if (comment == null) {
				_LOGGER.error("Error Happen During Delete");
				return Response.status(Response.Status.NOT_FOUND).entity("Error Happen During Delete").build();
			}
		} catch (SQLException | NumberFormatException e) {
//			_LOGGER.error("Error is " , e);
//			_LOGGER.error(e.getStackTrace().toString());
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("Error Happen while connect with database")
					.build();
		}
		_LOGGER.info("Succesful Request");
		_LOGGER.info(comment.toString());
		return Response.status(Response.Status.OK).entity(comment).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateComment(@PathParam("id") String id) {
		int commentId;
		try {
			_LOGGER.info("id is " + id);
			commentId = Integer.parseInt(id);
			if (!dao.updateCommentById(commentId)) {
				_LOGGER.error("Error Happen During Update");
				return Response.status(Response.Status.NOT_MODIFIED).entity("Error Happen During Update").build();
			}
		} catch (SQLException | NumberFormatException e) {
//			_LOGGER.error("Error is " , e);
//			_LOGGER.error(e.getStackTrace().toString());
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("Error Happen while connect with database")
					.build();
		}
		_LOGGER.info("Succesful Request");
		return Response.status(Response.Status.OK).entity("Succesful Request").build();
	}

}