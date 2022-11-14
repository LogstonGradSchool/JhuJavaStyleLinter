/**
 * This program lints Java programs in accordance with the style guide put forth
 * by the Professor of EN.605.201.
 *
 * @author Paul Bulkley-Logston
 **/
package com.logston.jhu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
      "--error-on-unordered"
   }, description = "Exit with an error of 201 if any class is unordered.")
   private boolean errorOnUnordered = false;

   protected List<String> getParameters() {
      return this.parameters;
   }

   protected boolean getErrorOnUnordered() {
      return this.errorOnUnordered;
   }
}


class Pair<K, V> {
   private K k;
   private V v;

   Pair(K k, V v) {
      this.k = k;
      this.v = v;
   }

   protected K getK() {
      return k;
   }

   protected V getV() {
      return v;
   }
}


public class StyleLinter {
   private static Options options;

   public static void main(String[] args) {
      options = new Options();

      JCommander.newBuilder().addObject(options).build().parse(args);

      Boolean anyUnordered = false;
      HashMap<String, String> results = new HashMap<String, String>();
      for (var path : options.getParameters()) {
         Pair<String, Boolean> result = runSingleFile(path);
         results.put(path, result.getK());
         if (result.getV()) {
            anyUnordered = true;
            System.out.printf("%s,%s\n", path, result.getK());
         }
      }

      if (options.getErrorOnUnordered() && anyUnordered) {
         System.exit(201);
      }
   }

   private static Pair<String, Boolean> runSingleFile(String path) {
      ParseResult<CompilationUnit> pr = null;
      try {
         pr = (new JavaParser()).parse(new File(path));
      } catch (IOException e) {
         return new Pair<String, Boolean>("Unable to parse file", false);
      }

      if (!pr.isSuccessful()) {
         return new Pair<String, Boolean>("Parse not successful.", false);
      }

      VisibilityChecker vc = new VisibilityChecker(pr.getResult().get());

      return new Pair<String, Boolean>(
         String.format("%d,%d", vc.getUnorderedClasses(), vc.getTotalClasses()),
         (vc.getUnorderedClasses() > 0));
   }
}
