/**
 * Copyright 2005-2010 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package org.restlet.engine.connector;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.Message;
import org.restlet.Response;
import org.restlet.data.Parameter;
import org.restlet.engine.io.BufferState;
import org.restlet.engine.io.CompletionListener;
import org.restlet.engine.io.IoState;
import org.restlet.util.SelectionListener;
import org.restlet.util.SelectionRegistration;
import org.restlet.util.Series;

/**
 * A network connection way though which messages are exchanged. Messages can be
 * either sent or received, requests or responses.
 * 
 * @author Jerome Louvel
 */
public abstract class Way implements SelectionListener, CompletionListener {

    /** The byte buffer. */
    private final ByteBuffer byteBuffer;

    /** The byte buffer IO state. */
    private volatile BufferState byteBufferState;

    /** The parent connection. */
    private final Connection<?> connection;

    /** The message headers. */
    private volatile Series<Parameter> headers;

    /** The IO state. */
    private volatile IoState ioState;

    /** The line builder. */
    private final StringBuilder lineBuilder;

    /** The line builder state. */
    private volatile BufferState lineBuilderState;

    /** The current message exchanged. */
    private volatile Response message;

    /** The queue of messages. */
    private final Queue<Response> messages;

    /** The message state. */
    private volatile MessageState messageState;

    /** The NIO selection registration. */
    private volatile SelectionRegistration registration;

    /**
     * Constructor.
     * 
     * @param connection
     *            The parent connection.
     * @param bufferSize
     *            The byte buffer size.
     */
    public Way(Connection<?> connection, int bufferSize) {
        this.byteBuffer = connection.getHelper().isDirectBuffers() ? ByteBuffer
                .allocateDirect(bufferSize) : ByteBuffer.allocate(bufferSize);
        this.byteBufferState = BufferState.FILLING;
        this.lineBuilder = new StringBuilder();
        this.lineBuilderState = BufferState.IDLE;
        this.messages = new ConcurrentLinkedQueue<Response>();
        this.message = null;
        this.headers = null;
        this.connection = connection;
        this.messageState = MessageState.IDLE;
        this.ioState = IoState.IDLE;
        this.registration = new SelectionRegistration(0, this);
    }

    /**
     * Recycles the way so it can be reused. Typically invoked by a connection
     * pool.
     */
    public void clear() {
        this.byteBuffer.clear();
        this.byteBufferState = BufferState.FILLING;
        this.headers = null;
        this.ioState = IoState.IDLE;
        clearLineBuilder();
        this.message = null;
        this.messages.clear();
        this.messageState = MessageState.IDLE;
    }

    /**
     * Clears the line builder and adjust its state.
     */
    protected void clearLineBuilder() {
        getLineBuilder().delete(0, getLineBuilder().length());
        setLineBuilderState(BufferState.IDLE);
    }

    /**
     * Returns the actual message, request or response.
     * 
     * @return The actual message, request or response.
     */
    protected abstract Message getActualMessage();

    /**
     * Returns the byte buffer.
     * 
     * @return The byte buffer.
     */
    protected ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    /**
     * Returns the byte buffer IO state.
     * 
     * @return The byte buffer IO state.
     */
    protected BufferState getByteBufferState() {
        return byteBufferState;
    }

    /**
     * Returns the parent connection.
     * 
     * @return The parent connection.
     */
    protected Connection<?> getConnection() {
        return connection;
    }

    /**
     * Returns the response headers.
     * 
     * @return The response headers to be written.
     */
    protected Series<Parameter> getHeaders() {
        return headers;
    }

    /**
     * Returns the parent connector helper.
     * 
     * @return The parent connector helper.
     */
    protected ConnectionHelper<?> getHelper() {
        return getConnection().getHelper();
    }

    /**
     * Returns the IO state.
     * 
     * @return The IO state.
     */
    protected IoState getIoState() {
        return ioState;
    }

    /**
     * Returns the line builder.
     * 
     * @return The line builder.
     */
    protected StringBuilder getLineBuilder() {
        return lineBuilder;
    }

    /**
     * Returns the line builder state.
     * 
     * @return The line builder state.
     */
    protected BufferState getLineBuilderState() {
        return lineBuilderState;
    }

    /**
     * Returns the logger.
     * 
     * @return The logger.
     */
    protected Logger getLogger() {
        return getConnection().getLogger();
    }

    /**
     * Returns the current message processed.
     * 
     * @return The current message processed.
     */
    protected Response getMessage() {
        return message;
    }

    /**
     * Returns the queue of messages.
     * 
     * @return The queue of messages.
     */
    public Queue<Response> getMessages() {
        return messages;
    }

    /**
     * Returns the message state.
     * 
     * @return The message state.
     */
    protected MessageState getMessageState() {
        return messageState;
    }

    /**
     * Returns the socket's NIO registration holding the link between the
     * channel and the connection.
     * 
     * @return The socket's NIO registration holding the link between the
     *         channel and the connection.
     */
    protected SelectionRegistration getRegistration() {
        return registration;
    }

    /**
     * Returns the operations of interest.
     * 
     * @return The operations of interest.
     */
    protected abstract int getSocketInterestOps();

    /**
     * Indicates if the processing of the next message is possible.
     * 
     * @return True if the processing of the next message is possible.
     */
    protected boolean isProcessing() {
        return (getIoState() == IoState.PROCESSING)
                && (getMessageState() != MessageState.IDLE)
                && (getMessageState() != MessageState.END);
    }

    /**
     * Callback method invoked when the current message has been completely
     * received or sent.
     */
    public void onCompleted() {
        setIoState(IoState.IDLE);
        setMessageState(MessageState.IDLE);
        setMessage(null);
    }

    /**
     * Callback method invoked when the way has been selected for IO operations
     * it registered interest in.
     * 
     * @param registration
     *            The selection registration.
     */
    public void onSelected(SelectionRegistration registration) {
        if (getIoState() == IoState.INTEREST) {
            setIoState(IoState.PROCESSING);

            if (getMessageState() == MessageState.IDLE) {
                setMessageState(MessageState.START);
            }
        } else if (getIoState() == IoState.CANCELING) {
            setIoState(IoState.CANCELLED);
        }

        if (!getByteBuffer().hasRemaining()) {
            getByteBuffer().clear();
            setByteBufferState(BufferState.FILLING);
        }
    }

    /**
     * Reuses the way based on an updated connection.
     */
    public void reuse() {
    }

    /**
     * Sets the byte buffer IO state.
     * 
     * @param byteBufferState
     *            The byte buffer IO state.
     */
    protected void setByteBufferState(BufferState byteBufferState) {
        this.byteBufferState = byteBufferState;
    }

    /**
     * Sets the response headers to be written.
     * 
     * @param headers
     *            The response headers.
     */
    protected void setHeaders(Series<Parameter> headers) {
        this.headers = headers;
    }

    /**
     * Sets the IO state.
     * 
     * @param ioState
     *            The IO state.
     */
    protected void setIoState(IoState ioState) {
        this.ioState = ioState;
    }

    /**
     * Sets the line builder state.
     * 
     * @param lineBuilderState
     *            The line builder state.
     */
    protected void setLineBuilderState(BufferState lineBuilderState) {
        this.lineBuilderState = lineBuilderState;
    }

    /**
     * Sets the current message processed.
     * 
     * @param message
     *            The current message processed.
     */
    protected void setMessage(Response message) {
        this.message = message;
    }

    /**
     * Sets the message state.
     * 
     * @param messageState
     *            The message state.
     */
    protected void setMessageState(MessageState messageState) {
        if (this.messageState != messageState) {
            this.messageState = messageState;

            if (getLogger().isLoggable(Level.FINER)) {
                getLogger().finer(
                        "New message state: " + messageState + " ("
                                + hashCode() + ")");
            }
        }
    }

    /**
     * Sets the NIO selection registration holding the link between the
     * connection and the way.
     * 
     * @param registration
     *            The NIO selection registration holding the link between the
     *            connection and the way.
     */
    protected void setRegistration(SelectionRegistration registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return getIoState() + ", " + getMessageState();
    }

    /**
     * Updates the way IO and message states.
     */
    public void updateState() {
        getRegistration().setInterestOperations(getSocketInterestOps());
    }

}
