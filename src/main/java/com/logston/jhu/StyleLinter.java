/**
 * This program lints Java programs in accordance with the style guide put forth
 * by the Professor of EN.605.201.
 *
 * @author Paul Bulkley-Logston
 **/
package com.logston.jhu;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;


class Options {
   @Parameter
   private List<String> parameters = new ArrayList<>();

   @Parameter(names =
   {
      "--verbosity"
   }, description = "Level of verbosity")
   private Integer verbosity = 1;

   @Parameter(names = "--lsp", description = "Run LSP Server")
   private boolean runLsp = false;

   protected List<String> getParameters() {
      return this.parameters;
   }

   protected Integer getVerbosity() {
      return this.verbosity;
   }

   protected boolean getRunLsp() {
      return this.runLsp;
   }
}


public class StyleLinter {
   public static void main(String[] args) {
      Options options = new Options();
      JCommander.newBuilder().addObject(options).build().parse(args);

      if (options.getRunLsp()) {
         try {
            ServerLauncher.startServer(System.in, System.out);
         } catch (ExecutionException e) {
            System.exit(1);
         } catch (InterruptedException e) {
            System.exit(1);
         }
      } else {
         runSingleFile(options.getParameters().get(0));
      }
   }

   private static void runSingleFile(String path) {
      ParseResult<CompilationUnit> pr = null;
      try {
         pr = (new JavaParser()).parse(new File(path));
      } catch (IOException e) {
         System.err.printf("Unable to parse file: %s.\n", path);
         System.exit(1);
      }

      if (!pr.isSuccessful()) {
         System.err.printf("Parse not successful %s.\n", path);
         System.exit(1);
      }

      VisibilityChecker vc = new VisibilityChecker(pr.getResult().get());

      System.out.printf("%d,%d\n", vc.getUnorderedClasses(),
         vc.getTotalClasses());
   }
}
