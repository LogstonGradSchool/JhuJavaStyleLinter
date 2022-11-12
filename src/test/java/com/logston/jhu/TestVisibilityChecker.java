package com.logston.jhu;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.JavaParser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestVisibilityChecker {

   private final String inOrderFieldClass = """
      class FieldClass {
         public int publicVar = 0;
         protected int protectedVar = 0;
         private int privateVar = 0;
      }
      """;

   private final String inOrderFieldMethodClass = """
      class FieldMethodClass {
         public int publicVar = 0;
         public void publicMethod() {}
         protected int protectedVar = 0;
         protected void protectedMethod() {}
         private int privateVar = 0;
         private void privateMethod() {}
      }
      """;

   private final String privateBeforeProtectedClass = """
      class PrivateBeforeProtectedClass {
         private int privateVar = 0;
         private void privateMethod() {}
         protected int protectedVar = 0;
         protected void protectedMethod() {}
      }
      """;

   private final String protectedBeforePublicClass = """
      class ProtectedBeforePublicClass {
         protected int protectedVar = 0;
         protected void protectedMethod() {}
         public int publicVar = 0;
         public void publicMethod() {}
      }
      """;

   private final String privateBeforePublicClass = """
      class PrivateBeforePublicClass {
         private int privateVar = 0;
         private void privateMethod() {}
         public int publicVar = 0;
         public void publicMethod() {}
      }
      """;

   private final String reversedClass = """
      class ReversedClass {
         private int privateVar = 0;
         public void privateMethod() {}
         protected int protectedVar = 0;
         public void protectedMethod() {}
         public int publicVar = 0;
         public void publicMethod() {}
      }
      """;

   @Test
   public void testInOrderFieldClass() {
      CompilationUnit cu = this.getCompilationUnit(this.inOrderFieldClass);
      VisibilityChecker vc = new VisibilityChecker(cu);

      assertEquals(1, vc.getTotalClasses());
      assertEquals(0, vc.getUnorderedClasses());
   }

   @Test
   public void testInOrderFieldMethodClass() {
      CompilationUnit cu =
         this.getCompilationUnit(this.inOrderFieldMethodClass);
      VisibilityChecker vc = new VisibilityChecker(cu);

      assertEquals(1, vc.getTotalClasses());
      assertEquals(0, vc.getUnorderedClasses());
   }

   @Test
   public void testPrivateBeforeProtectedClass() {
      CompilationUnit cu =
         this.getCompilationUnit(this.privateBeforeProtectedClass);
      VisibilityChecker vc = new VisibilityChecker(cu);

      assertEquals(1, vc.getTotalClasses());
      assertEquals(1, vc.getUnorderedClasses());
   }

   @Test
   public void testProtectedBeforePublicClass() {
      CompilationUnit cu =
         this.getCompilationUnit(this.protectedBeforePublicClass);
      VisibilityChecker vc = new VisibilityChecker(cu);

      assertEquals(1, vc.getTotalClasses());
      assertEquals(1, vc.getUnorderedClasses());
   }

   @Test
   public void testPrivateBeforePublicClass() {
      CompilationUnit cu =
         this.getCompilationUnit(this.privateBeforePublicClass);
      VisibilityChecker vc = new VisibilityChecker(cu);

      assertEquals(1, vc.getTotalClasses());
      assertEquals(1, vc.getUnorderedClasses());
   }

   @Test
   public void testReversedClass() {
      CompilationUnit cu = this.getCompilationUnit(this.reversedClass);
      VisibilityChecker vc = new VisibilityChecker(cu);

      assertEquals(1, vc.getTotalClasses());
      assertEquals(1, vc.getUnorderedClasses());
   }

   private CompilationUnit getCompilationUnit(String s) {
      return (new JavaParser()).parse(s).getResult().get();
   }
}
