/**
 * @author Paul Bulkley-Logston
 **/
package com.logston.jhu;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

class VisibilityChecker {
   private CompilationUnit cu;
   protected int totalClasses = 0;
   protected int unorderedClasses = 0;

   VisibilityChecker(CompilationUnit cu) {
      this.cu = cu;
      this.evaluate();
   }

   private void evaluate() {
      for (var c : cu.findAll(ClassOrInterfaceDeclaration.class)) {
         this.totalClasses += 1;
         if (this.isUnordered(c) > 0) {
            this.unorderedClasses += 1;
         }
      }
   }

   private int isUnordered(ClassOrInterfaceDeclaration c) {
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
