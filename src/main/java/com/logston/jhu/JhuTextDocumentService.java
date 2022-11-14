/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.logston.jhu;

import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.services.TextDocumentService;

/**
 * @author lhein
 */
public class JhuTextDocumentService implements TextDocumentService {

   /**
    * The document open notification is sent from the client to the server to
    * signal newly opened text documents. The document's truth is now managed by
    * the client and the server must not try to read the document's truth using
    * the document's uri.
    *
    * Registration Options:
    * {@link org.eclipse.lsp4j.TextDocumentRegistrationOptions}
    */
   @JsonNotification
   public void didOpen(DidOpenTextDocumentParams params) {
      System.out.println("Called didOpen");
      System.out.println(params.toString());
      return;
   }

   /**
    * The document change notification is sent from the client to the server to
    * signal changes to a text document.
    *
    * Registration Options:
    * {@link org.eclipse.lsp4j.TextDocumentChangeRegistrationOptions}
    */
   @JsonNotification
   public void didChange(DidChangeTextDocumentParams params) {
      System.out.println("Called didChange");
      System.out.println(params.toString());
      return;
   }

   /**
    * The document close notification is sent from the client to the server when
    * the document got closed in the client. The document's truth now exists
    * where the document's uri points to (e.g. if the document's uri is a file
    * uri the truth now exists on disk).
    *
    * Registration Options:
    * {@link org.eclipse.lsp4j.TextDocumentRegistrationOptions}
    */
   @JsonNotification
   public void didClose(DidCloseTextDocumentParams params) {
      System.out.println("Called didClose");
      System.out.println(params.toString());
      return;
   }

   /**
    * The document save notification is sent from the client to the server when
    * the document for saved in the client.
    *
    * Registration Options:
    * {@link org.eclipse.lsp4j.TextDocumentSaveRegistrationOptions}
    */
   @JsonNotification
   public void didSave(DidSaveTextDocumentParams params) {
      System.out.println("Called didSave");
      System.out.println(params.toString());
      return;
   }
}
