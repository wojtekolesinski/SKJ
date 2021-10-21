package tiia.pjatk.skj.client.sender;

import tiia.pjatk.skj.client.handler.ResponseHandler;

import java.io.IOException;

public interface RequestSender {

    ResponseHandler sendRequest(String command) throws IOException;
}
