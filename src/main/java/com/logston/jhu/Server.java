package com.logston.jhu;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Standard IO Launcher for Ballerina Language Server.
 */
class ServerLauncher {
   private static final Logger LOGGER =
      LoggerFactory.getLogger(AbstractLanguageServer.class);

   /**
    * Starts the language server given the input and output streams to read and
    * write messages.
    *
    * @param in input stream.
    * @param out output stream.
    * @throws InterruptedException
    * @throws ExecutionException
    */
   public static void startServer(InputStream in, OutputStream out)
      throws InterruptedException, ExecutionException {
      LOGGER.warn("GETTING IT GOING!");
      System.out.println("HEYAHAHAH");
      JhuLanguageServer server = new JhuLanguageServer();
      Launcher<LanguageClient> launcher =
         LSPLauncher.createServerLauncher(server, in, out);
      server.connect(launcher.getRemoteProxy());
      launcher.startListening();
   }
}
