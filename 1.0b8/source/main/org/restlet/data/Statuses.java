/*
 * Copyright 2005-2006 J�r�me LOUVEL
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * http://www.opensource.org/licenses/cddl1.txt
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * http://www.opensource.org/licenses/cddl1.txt
 * If applicable, add the following below this CDDL
 * HEADER, with the fields enclosed by brackets "[]"
 * replaced with your own identifying information:
 * Portions Copyright [yyyy] [name of copyright owner]
 */

package org.restlet.data;

/**
 * Enumeration of call status.
 */
public enum Statuses implements Status
{
   INFO_CONTINUE, INFO_SWITCHING_PROTOCOL, INFO_PROCESSING,

   SUCCESS_OK, SUCCESS_CREATED, SUCCESS_ACCEPTED, SUCCESS_NON_AUTHORITATIVE, SUCCESS_NO_CONTENT,
   SUCCESS_RESET_CONTENT, SUCCESS_PARTIAL_CONTENT, SUCCESS_MULTI_STATUS,

   REDIRECTION_MULTIPLE_CHOICES, REDIRECTION_MOVED_PERMANENTLY, REDIRECTION_FOUND, 
   REDIRECTION_SEE_OTHER, REDIRECTION_NOT_MODIFIED, REDIRECTION_USE_PROXY, REDIRECTION_MOVED_TEMPORARILY,

   CLIENT_ERROR_BAD_REQUEST, CLIENT_ERROR_UNAUTHORIZED, CLIENT_ERROR_PAYMENT_REQUIRED,
   CLIENT_ERROR_FORBIDDEN, CLIENT_ERROR_NOT_FOUND, CLIENT_ERROR_METHOD_NOT_ALLOWED,
   CLIENT_ERROR_NOT_ACCEPTABLE, CLIENT_ERROR_PROXY_AUTHENTIFICATION_REQUIRED,
   CLIENT_ERROR_REQUEST_TIMEOUT, CLIENT_ERROR_CONFLICT, CLIENT_ERROR_GONE, CLIENT_ERROR_LENGTH_REQUIRED,
   CLIENT_ERROR_PRECONDITION_FAILED, CLIENT_ERROR_REQUEST_ENTITY_TOO_LARGE, CLIENT_ERROR_REQUEST_URI_TOO_LONG,
   CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE, CLIENT_ERROR_REQUESTED_RANGE_NOT_SATISFIABLE,
   CLIENT_ERROR_EXPECTATION_FAILED, CLIENT_ERROR_UNPROCESSABLE_ENTITY, CLIENT_ERROR_LOCKED,
   CLIENT_ERROR_FAILED_DEPENDENCY,

   SERVER_ERROR_INTERNAL, SERVER_ERROR_NOT_IMPLEMENTED, SERVER_ERROR_BAD_GATEWAY,
   SERVER_ERROR_SERVICE_UNAVAILABLE, SERVER_ERROR_GATEWAY_TIMEOUT, SERVER_ERROR_HTTP_VERSION_NOT_SUPPORTED,
   SERVER_ERROR_INSUFFICIENT_STORAGE;

   /**
    * Returns the HTTP code.
    * @return The HTTP code.
    */
   public int getHttpCode()
   {
      int result = 0;

      switch(this)
      {
         case INFO_CONTINUE:
            result = 100;
            break;
         case INFO_SWITCHING_PROTOCOL:
            result = 101;
            break;
         case INFO_PROCESSING:
            result = 102;
            break;

         case SUCCESS_OK:
            result = 200;
            break;
         case SUCCESS_CREATED:
            result = 201;
            break;
         case SUCCESS_ACCEPTED:
            result = 202;
            break;
         case SUCCESS_NON_AUTHORITATIVE:
            result = 203;
            break;
         case SUCCESS_NO_CONTENT:
            result = 204;
            break;
         case SUCCESS_RESET_CONTENT:
            result = 205;
            break;
         case SUCCESS_PARTIAL_CONTENT:
            result = 206;
            break;
         case SUCCESS_MULTI_STATUS:
            result = 207;
            break;

         case REDIRECTION_MULTIPLE_CHOICES:
            result = 300;
            break;
         case REDIRECTION_MOVED_PERMANENTLY:
            result = 301;
            break;
         case REDIRECTION_FOUND:
            result = 302;
            break;
         case REDIRECTION_SEE_OTHER:
            result = 303;
            break;
         case REDIRECTION_NOT_MODIFIED:
            result = 304;
            break;
         case REDIRECTION_USE_PROXY:
            result = 305;
            break;
         case REDIRECTION_MOVED_TEMPORARILY:
            result = 307;
            break;

         case CLIENT_ERROR_BAD_REQUEST:
            result = 400;
            break;
         case CLIENT_ERROR_UNAUTHORIZED:
            result = 401;
            break;
         case CLIENT_ERROR_PAYMENT_REQUIRED:
            result = 402;
            break;
         case CLIENT_ERROR_FORBIDDEN:
            result = 403;
            break;
         case CLIENT_ERROR_NOT_FOUND:
            result = 404;
            break;
         case CLIENT_ERROR_METHOD_NOT_ALLOWED:
            result = 405;
            break;
         case CLIENT_ERROR_NOT_ACCEPTABLE:
            result = 406;
            break;
         case CLIENT_ERROR_PROXY_AUTHENTIFICATION_REQUIRED:
            result = 407;
            break;
         case CLIENT_ERROR_REQUEST_TIMEOUT:
            result = 408;
            break;
         case CLIENT_ERROR_CONFLICT:
            result = 409;
            break;
         case CLIENT_ERROR_GONE:
            result = 410;
            break;
         case CLIENT_ERROR_LENGTH_REQUIRED:
            result = 411;
            break;
         case CLIENT_ERROR_PRECONDITION_FAILED:
            result = 412;
            break;
         case CLIENT_ERROR_REQUEST_ENTITY_TOO_LARGE:
            result = 413;
            break;
         case CLIENT_ERROR_REQUEST_URI_TOO_LONG:
            result = 414;
            break;
         case CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE:
            result = 415;
            break;
         case CLIENT_ERROR_REQUESTED_RANGE_NOT_SATISFIABLE:
            result = 416;
            break;
         case CLIENT_ERROR_EXPECTATION_FAILED:
            result = 417;
            break;
         case CLIENT_ERROR_UNPROCESSABLE_ENTITY:
            result = 422;
            break;
         case CLIENT_ERROR_LOCKED:
            result = 423;
            break;
         case CLIENT_ERROR_FAILED_DEPENDENCY:
            result = 424;
            break;

         case SERVER_ERROR_INTERNAL:
            result = 500;
            break;
         case SERVER_ERROR_NOT_IMPLEMENTED:
            result = 501;
            break;
         case SERVER_ERROR_BAD_GATEWAY:
            result = 502;
            break;
         case SERVER_ERROR_SERVICE_UNAVAILABLE:
            result = 503;
            break;
         case SERVER_ERROR_GATEWAY_TIMEOUT:
            result = 504;
            break;
         case SERVER_ERROR_HTTP_VERSION_NOT_SUPPORTED:
            result = 505;
            break;
         case SERVER_ERROR_INSUFFICIENT_STORAGE:
            result = 507;
            break;
      }

      return result;
   }

   /**
    * Returns the URI of the specification describing the status.
    * @return The URI of the specification describing the status.
    */
   public String getUri()
   {
      return getUri(getHttpCode());
   }

   /**
    * Returns the URI of the specification describing the status.
    * @param httpCode The HTTP code of the status.
    * @return The URI of the specification describing the status.
    */
   public static String getUri(int httpCode)
   {
      String result = null;
      String httpRoot = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html";
      String webDavRoot = "http://www.webdav.org/specs/rfc2518.html";

      switch(httpCode)
      {
         case 100:
            result = httpRoot + "#sec10.1.1";
            break;
         case 101:
            result = httpRoot + "#sec10.1.2";
            break;
         case 102:
            result = webDavRoot + "#STATUS_102";
            break;

         case 200:
            result = httpRoot + "#sec10.2.1";
            break;
         case 201:
            result = httpRoot + "#sec10.2.2";
            break;
         case 202:
            result = httpRoot + "#sec10.2.3";
            break;
         case 203:
            result = httpRoot + "#sec10.2.4";
            break;
         case 204:
            result = httpRoot + "#sec10.2.5";
            break;
         case 205:
            result = httpRoot + "#sec10.2.6";
            break;
         case 206:
            result = httpRoot + "#sec10.2.7";
            break;
         case 207:
            result = webDavRoot + "#STATUS_207";
            break;

         case 300:
            result = httpRoot + "#sec10.3.1";
            break;
         case 301:
            result = httpRoot + "#sec10.3.2";
            break;
         case 302:
            result = httpRoot + "#sec10.3.3";
            break;
         case 303:
            result = httpRoot + "#sec10.3.4";
            break;
         case 304:
            result = httpRoot + "#sec10.3.5";
            break;
         case 305:
            result = httpRoot + "#sec10.3.6";
            break;
         case 307:
            result = httpRoot + "#sec10.3.8";
            break;

         case 400:
            result = httpRoot + "#sec10.4.1";
            break;
         case 401:
            result = httpRoot + "#sec10.4.2";
            break;
         case 402:
            result = httpRoot + "#sec10.4.3";
            break;
         case 403:
            result = httpRoot + "#sec10.4.4";
            break;
         case 404:
            result = httpRoot + "#sec10.4.5";
            break;
         case 405:
            result = httpRoot + "#sec10.4.6";
            break;
         case 406:
            result = httpRoot + "#sec10.4.7";
            break;
         case 407:
            result = httpRoot + "#sec10.4.8";
            break;
         case 408:
            result = httpRoot + "#sec10.4.9";
            break;
         case 409:
            result = httpRoot + "#sec10.4.10";
            break;
         case 410:
            result = httpRoot + "#sec10.4.11";
            break;
         case 411:
            result = httpRoot + "#sec10.4.12";
            break;
         case 412:
            result = httpRoot + "#sec10.4.13";
            break;
         case 413:
            result = httpRoot + "#sec10.4.14";
            break;
         case 414:
            result = httpRoot + "#sec10.4.15";
            break;
         case 415:
            result = httpRoot + "#sec10.4.16";
            break;
         case 416:
            result = httpRoot + "#sec10.4.17";
            break;
         case 417:
            result = httpRoot + "#sec10.4.18";
            break;
         case 422:
            result = webDavRoot + "#STATUS_422";
            break;
         case 423:
            result = webDavRoot + "#STATUS_423";
            break;
         case 424:
            result = webDavRoot + "#STATUS_424";
            break;

         case 500:
            result = httpRoot + "#sec10.5.1";
            break;
         case 501:
            result = httpRoot + "#sec10.5.2";
            break;
         case 502:
            result = httpRoot + "#sec10.5.3";
            break;
         case 503:
            result = httpRoot + "#sec10.5.4";
            break;
         case 504:
            result = httpRoot + "#sec10.5.5";
            break;
         case 505:
            result = httpRoot + "#sec10.5.6";
            break;
         case 507:
            result = webDavRoot + "#STATUS_507";
            break;
      }

      return result;
   }

   /**
    * Returns the name of this REST element.
    * @return The name of this REST element.
    */
   public String getName()
   {
      return getName(getHttpCode());
   }

   /**
    * Returns the description of this REST element.
    * @return The description of this REST element.
    */
   public String getDescription()
   {
      return getDescription(getHttpCode());
   }

   /**
    * Returns the name of this REST element.
    * @param httpCode The HTTP code of the status.
    * @return The name of this REST element.
    */
   public static String getName(int httpCode)
   {
      String result = null;

      switch(httpCode)
      {
         case 100:
            result = "Continue";
            break;
         case 101:
            result = "Switching Protocols";
            break;
         case 102:
            result = "Processing";
            break;

         case 200:
            result = "OK";
            break;
         case 201:
            result = "Created";
            break;
         case 202:
            result = "Accepted";
            break;
         case 203:
            result = "Non-Authoritative Information";
            break;
         case 204:
            result = "No Content";
            break;
         case 205:
            result = "Reset Content";
            break;
         case 206:
            result = "Partial Content";
            break;
         case 207:
            result = "Multi-Status";
            break;

         case 300:
            result = "Multiple Choices";
            break;
         case 301:
            result = "Moved Permanently";
            break;
         case 302:
            result = "Found";
            break;
         case 303:
            result = "See Other";
            break;
         case 304:
            result = "Not Modified";
            break;
         case 305:
            result = "Use Proxy";
            break;
         case 307:
            result = "Temporary Redirect";
            break;

         case 400:
            result = "Bad Request";
            break;
         case 401:
            result = "Unauthorized";
            break;
         case 402:
            result = "Payment Required";
            break;
         case 403:
            result = "Forbidden";
            break;
         case 404:
            result = "Not Found";
            break;
         case 405:
            result = "Method Not Allowed";
            break;
         case 406:
            result = "Not Acceptable";
            break;
         case 407:
            result = "Proxy Authentication Required";
            break;
         case 408:
            result = "Request Timeout";
            break;
         case 409:
            result = "Conflict";
            break;
         case 410:
            result = "Gone";
            break;
         case 411:
            result = "Length Required";
            break;
         case 412:
            result = "Precondition Failed";
            break;
         case 413:
            result = "Request Entity Too Large";
            break;
         case 414:
            result = "Request URI Too Long";
            break;
         case 415:
            result = "Unsupported Media Type";
            break;
         case 416:
            result = "Requested Range Not Satisfiable";
            break;
         case 417:
            result = "Expectation Failed";
            break;
         case 422:
            result = "Unprocessable Entity";
            break;
         case 423:
            result = "Locked";
            break;
         case 424:
            result = "Failed Dependency";
            break;

         case 500:
            result = "Internal Server Error";
            break;
         case 501:
            result = "Not Implemented";
            break;
         case 502:
            result = "Bad Gateway";
            break;
         case 503:
            result = "Service Unavailable";
            break;
         case 504:
            result = "Gateway Timeout";
            break;
         case 505:
            result = "HTTP Version Not Supported";
            break;
         case 507:
            result = "Insufficient Storage";
            break;
      }

      return result;
   }

   /**
    * Returns the description of this REST element.
    * @param httpCode The HTTP code of the status.
    * @return The description of this REST element.
    */
   public static String getDescription(int httpCode)
   {
      String result = null;

      switch(httpCode)
      {
         case 100:
            result = "The client should continue with its request";
            break;
         case 101:
            result = "The server is willing to change the application protocol being used on this connection";
            break;
         case 102:
            result = "Interim response used to inform the client that the server has accepted the complete request, but has not yet completed it";
            break;

         case 200:
            result = "The request has succeeded";
            break;
         case 201:
            result = "The request has been fulfilled and resulted in a new resource being created";
            break;
         case 202:
            result = "The request has been accepted for processing, but the processing has not been completed";
            break;
         case 203:
            result = "The returned metainformation is not the definitive set as available from the origin server";
            break;
         case 204:
            result = "The server has fulfilled the request but does not need to return an entity-body, and might want to return updated metainformation";
            break;
         case 205:
            result = "The server has fulfilled the request and the user agent should reset the document view which caused the request to be sent";
            break;
         case 206:
            result = "The server has fulfilled the partial get request for the resource";
            break;
         case 207:
            result = "Provides status for multiple independent operations";
            break;

         case 300:
            result = "The requested resource corresponds to any one of a set of representations";
            break;
         case 301:
            result = "The requested resource has been assigned a new permanent URI";
            break;
         case 302:
            result = "The requested resource can be found under a different URI";
            break;
         case 303:
            result = "The response to the request can be found under a different URI";
            break;
         case 304:
            result = "The client has performed a conditional GET request and access is allowed";
            break;
         case 305:
            result = "The requested resource must be accessed through the proxy given by the location field";
            break;
         case 307:
            result = "The requested resource resides temporarily under a different URI";
            break;

         case 400:
            result = "The request could not be understood by the server due to malformed syntax";
            break;
         case 401:
            result = "The request requires user authentication";
            break;
         case 402:
            result = "This code is reserved for future use";
            break;
         case 403:
            result = "The server understood the request, but is refusing to fulfill it";
            break;
         case 404:
            result = "The server has not found anything matching the request URI";
            break;
         case 405:
            result = "The method specified in the Request-Line is not allowed for the resource identified by the request URI";
            break;
         case 406:
            result = "The resource identified by the request is only capable of generating response entities which have content characteristics not acceptable according to the accept headers sent in the request";
            break;
         case 407:
            result = "This code is similar to Unauthorized, but indicates that the client must first authenticate itself with the proxy";
            break;
         case 408:
            result = "The client did not produce a request within the time that the server was prepared to wait";
            break;
         case 409:
            result = "The request could not be completed due to a conflict with the current state of the resource";
            break;
         case 410:
            result = "The requested resource is no longer available at the server and no forwarding address is known";
            break;
         case 411:
            result = "The server refuses to accept the request without a defined content length";
            break;
         case 412:
            result = "The precondition given in one or more of the request header fields evaluated to false when it was tested on the server";
            break;
         case 413:
            result = "The server is refusing to process a request because the request entity is larger than the server is willing or able to process";
            break;
         case 414:
            result = "The server is refusing to service the request because the request URI is longer than the server is willing to interpret";
            break;
         case 415:
            result = "The server is refusing to service the request because the entity of the request is in a format not supported by the requested resource for the requested method";
            break;
         case 416:
            result = "For byte ranges, this means that the first byte position were greater than the current length of the selected resource";
            break;
         case 417:
            result = "The expectation given in the request header could not be met by this server";
            break;
         case 422:
            result = "The server understands the content type of the request entity and the syntax of the request entity is correct but was unable to process the contained instructions";
            break;
         case 423:
            result = "The source or destination resource of a method is locked";
            break;
         case 424:
            result = "The method could not be performed on the resource because the requested action depended on another action and that action failed";
            break;

         case 500:
            result = "The server encountered an unexpected condition which prevented it from fulfilling the request";
            break;
         case 501:
            result = "The server does not support the functionality required to fulfill the request";
            break;
         case 502:
            result = "The server, while acting as a gateway or proxy, received an invalid response from the upstream server it accessed in attempting to fulfill the request";
            break;
         case 503:
            result = "The server is currently unable to handle the request due to a temporary overloading or maintenance of the server";
            break;
         case 504:
            result = "The server, while acting as a gateway or proxy, did not receive a timely response from the upstream server specified by the URI (e.g. HTTP, FTP, LDAP) or some other auxiliary server (e.g. DNS) it needed to access in attempting to complete the request";
            break;
         case 505:
            result = "The server does not support, or refuses to support, the HTTP protocol version that was used in the request message";
            break;
         case 507:
            result = "The method could not be performed on the resource because the server is unable to store the representation needed to successfully complete the request";
            break;
      }

      return result;
   }

   /**
    * Indicates if the status is an information status.
    * @return True if the status is an information status.
    */
   public boolean isInfo()
   {
      return isInfo(getHttpCode());
   }

   /**
    * Indicates if the status is a success status.
    * @return True if the status is a success status.
    */
   public boolean isSuccess()
   {
      return isSuccess(getHttpCode());
   }

   /**
    * Indicates if the status is a redirection status.
    * @return True if the status is a redirection status.
    */
   public boolean isRedirection()
   {
      return isRedirection(getHttpCode());
   }

   /**
    * Indicates if the status is a client error status.
    * @return True if the status is a client error status.
    */
   public boolean isClientError()
   {
      return isClientError(getHttpCode());
   }

   /**
    * Indicates if the status is a server error status.
    * @return True if the status is a server error status.
    */
   public boolean isServerError()
   {
      return isServerError(getHttpCode());
   }

   /**
    * Indicates if the status is an error (client or server) status.
    * @return True if the status is an error (client or server) status.
    */
   public boolean isError()
   {
      return isError(getHttpCode());
   }

   /**
    * Indicates if the status is an information status.
    * @param httpCode The HTTP code of the status.
    * @return True if the status is an information status.
    */
   public static boolean isInfo(int httpCode)
   {
      boolean result = false;

      switch(httpCode)
      {
         case 100:
         case 101:
         case 102:
            result = true;
         break;
      }

      return result;
   }

   /**
    * Indicates if the status is a success status.
    * @param httpCode The HTTP code of the status.
    * @return True if the status is a success status.
    */
   public static boolean isSuccess(int httpCode)
   {
      boolean result = false;

      switch(httpCode)
      {
         case 200:
         case 201:
         case 202:
         case 203:
         case 204:
         case 205:
         case 206:
         case 207:
            result = true;
         break;
      }

      return result;
   }

   /**
    * Indicates if the status is a redirection status.
    * @param httpCode The HTTP code of the status.
    * @return True if the status is a redirection status.
    */
   public static boolean isRedirection(int httpCode)
   {
      boolean result = false;

      switch(httpCode)
      {
         case 300:
         case 301:
         case 302:
         case 303:
         case 304:
         case 305:
         case 307:
            result = true;
         break;
      }

      return result;
   }

   /**
    * Indicates if the status is a client error status.
    * @param httpCode The HTTP code of the status.
    * @return True if the status is a client error status.
    */
   public static boolean isClientError(int httpCode)
   {
      boolean result = false;

      switch(httpCode)
      {
         case 400:
         case 401:
         case 402:
         case 403:
         case 404:
         case 405:
         case 406:
         case 407:
         case 408:
         case 409:
         case 410:
         case 411:
         case 412:
         case 413:
         case 414:
         case 415:
         case 416:
         case 417:
         case 422:
         case 423:
         case 424:
            result = true;
         break;
      }

      return result;
   }

   /**
    * Indicates if the status is a server error status.
    * @param httpCode The HTTP code of the status.
    * @return True if the status is a server error status.
    */
   public static boolean isServerError(int httpCode)
   {
      boolean result = false;

      switch(httpCode)
      {
         case 500:
         case 501:
         case 502:
         case 503:
         case 504:
         case 505:
         case 507:
            result = true;
         break;
      }

      return result;
   }

   /**
    * Indicates if the status is an error (client or server) status.
    * @param httpCode The HTTP code of the status.
    * @return True if the status is an error (client or server) status.
    */
   public static boolean isError(int httpCode)
   {
      return isClientError(httpCode) || isServerError(httpCode);
   }

   /**
    * Indicates if the status is equal to a given one.
    * @param status The status to compare to.
    * @return True if the status is equal to a given one.
    */
   public boolean equals(Status status)
   {
      return getHttpCode() == status.getHttpCode();
   }

   /**
    * Returns the name of the status followed by its HTTP code.
    * @param httpCode The HTTP code of the status.
    * @return The name of the status followed by its HTTP code.
    */
   public static String toString(int httpCode)
   {
   	return getName(httpCode) + " (" + httpCode + ")";
   }

   /**
    * Returns the name of the status followed by its HTTP code.
    * @return The name of the status followed by its HTTP code.
    */
   public String toString()
   {
   	return toString(getHttpCode());
   }

}
