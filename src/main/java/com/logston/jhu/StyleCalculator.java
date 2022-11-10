/**
 * @author Paul Bulkley-Logston
 **/
package com.logston.jhu;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ParseResult;

public class StyleCalculator {
   private static final ParserConfiguration parserConfiguration =
      new ParserConfiguration();

   public static void main(String[] args) {
      final Path root = Paths.get(args[0]);
      final SourceRoot sourceRoot = new SourceRoot(root, parserConfiguration);

      List<ParseResult<CompilationUnit>> parseResults =
         new ArrayList<ParseResult<CompilationUnit>>();

      try {
         parseResults = sourceRoot.tryToParse("");
      } catch (IOException e) {
         System.err.println(e);
         System.exit(1);
      }

      List<CompilationUnit> allCUs =
         parseResults.stream().filter(ParseResult::isSuccessful)
            .map(r -> r.getResult().get()).collect(Collectors.toList());

      int outOfOrders = 0;
      int totalClass = 0;
      for (CompilationUnit cu : allCUs) {
         for (var c : cu.findAll(ClassOrInterfaceDeclaration.class)) {
            totalClass += 1;
            if (isOutOfOrder(c) > 0) {
               System.out.printf("%s:%s\n", cu.getStorage().get().getFileName(),
                  c.getName());
               outOfOrders += 1;
            }
         }
      }

      System.out.printf("Total Classes: %d\n", totalClass);
      System.out.printf("Class with private before non-private: %d\n",
         outOfOrders);
      System.out.printf("Fraction: %f\n",
         Double.valueOf(outOfOrders) / totalClass);
   } // end main()

   static private int isOutOfOrder(ClassOrInterfaceDeclaration c) {
      boolean seenPrivate = false;
      for (var member : c.getMembers()) {
         boolean isPrivate = false;
         if (member.isCallableDeclaration()) {
            isPrivate = member.asCallableDeclaration().isPrivate();
         } else if (member.isFieldDeclaration()) {
            isPrivate = member.asFieldDeclaration().isPrivate();
         } else {
            continue;
         }

         if (isPrivate && !seenPrivate) {
            seenPrivate = true;
         } else if (!isPrivate && seenPrivate) {
            return 1;
         }
      }

      return 0;
   }
}
