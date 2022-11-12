/**
 * This program lints Java programs in accordance with the style guide put forth
 * by the Professor of EN.605.201.
 *
 * @author Paul Bulkley-Logston
 **/
package com.logston.jhu;

import java.io.File;
import java.io.IOException;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;

public class StyleLinter {
   public static void main(String[] args) {
      ParseResult<CompilationUnit> pr = null;
      try {
         pr = (new JavaParser()).parse(new File(args[0]));
      } catch (IOException e) {
         System.err.printf("Unable to parse file: %s.\n", args[0]);
         System.exit(1);
      }

      if (!pr.isSuccessful()) {
         System.err.printf("Parse not successful %s.\n", args[0]);
         System.exit(1);
      }

      VisibilityChecker vc = new VisibilityChecker(pr.getResult().get());

      System.out.printf("%d,%d\n", vc.getUnorderedClasses(),
         vc.getTotalClasses());
   }
}
