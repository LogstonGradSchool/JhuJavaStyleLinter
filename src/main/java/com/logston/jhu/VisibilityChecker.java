/**
 * @author Paul Bulkley-Logston
 **/
package com.logston.jhu;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;


enum visibility {
   PRIVATE, PROTECTED, PUBLIC, UNKNOWN,
}


class VisibilityChecker {
   private CompilationUnit cu;
   private int totalClasses = 0;
   private int unorderedClasses = 0;

   VisibilityChecker(CompilationUnit cu) {
      this.cu = cu;
      this.evaluate();
   }

   protected int getTotalClasses() {
      return this.totalClasses;
   }

   protected int getUnorderedClasses() {
      return this.unorderedClasses;
   }

   private void evaluate() {
      for (var c : cu.findAll(ClassOrInterfaceDeclaration.class)) {
         this.totalClasses += 1;
         if (this.isUnordered(c)) {
            this.unorderedClasses += 1;
         }
      }
   }

   private visibility getMemberType(BodyDeclaration<?> member) {
      if (member.isCallableDeclaration()) {
         if (member.asCallableDeclaration().isPrivate()) {
            return visibility.PRIVATE;
         } else if (member.asCallableDeclaration().isProtected()) {
            return visibility.PROTECTED;
         } else if (member.asCallableDeclaration().isPublic()) {
            return visibility.PUBLIC;
         }
      } else if (member.isFieldDeclaration()) {
         if (member.asFieldDeclaration().isPrivate()) {
            return visibility.PRIVATE;
         } else if (member.asFieldDeclaration().isProtected()) {
            return visibility.PROTECTED;
         } else if (member.asFieldDeclaration().isPublic()) {
            return visibility.PUBLIC;
         }
      }

      return visibility.UNKNOWN;
   }

   private boolean isUnordered(ClassOrInterfaceDeclaration c) {
      visibility seenVis = visibility.UNKNOWN;

      for (var member : c.getMembers()) {
         visibility vis = this.getMemberType(member);

         // Skip checking unknown visibilities.
         if (vis == visibility.UNKNOWN) {
            continue;
         }

         if (seenVis == visibility.PROTECTED) {
            // If we've seen PROTECTED and now see PUBLIC, the class is out of
            // order.
            if (vis == visibility.PUBLIC) {
               return true;
            }
         } else if (seenVis == visibility.PRIVATE) {
            // If we've seen PRIVATE and now see PUBLIC or PROTECTED, the class
            // is out of order.
            if ((vis == visibility.PUBLIC) || (vis == visibility.PROTECTED)) {
               return true;
            }
         }

         seenVis = vis;
      }

      return false;
   }
}
