package org;
import java.util.Arrays;
import java.util.Random;

import org.ejml.EjmlUnitTests;
import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.RandomMatrices_DDRM;

//import static org.junit.jupiter.api.Assertions.assertEquals;

public class App {
    static int NUM_PTS = 9;
    static Random rand = new Random(7264);

    public static void main(String[] args) throws Exception {
      System.out.println("Hello, World!");
  
      System.out.println("Give it a simple function and see if it computes something close to it for its results.");
      System.out.println("------------------------------------------------------------------------------------------");
      Random rand = new Random(7264);
 
      //DMatrixRMaj param = new DMatrixRMaj(3,1, true, 2, -1, 4);
      DMatrixRMaj param = new DMatrixRMaj(3,1, true, 10, -4, 105.2,10,4,25,10,2,52);

      System.out.println("parma is: " + param);

      LevenbergMarquardt alg = new LevenbergMarquardt(1);
      alg.setConvergence(20,1e-12,1e-12);
      
      //DMatrixRMaj X = RandomMatrices_DDRM.rectangle(NUM_PTS,3,rand);
      DMatrixRMaj X = new DMatrixRMaj(9,3, true,
      .181024406,  .449489533,  .463171928,
      .375523475,  .659373946,  .689717738,
      .922691834,  .944987968,  .184904894,
      .876445087,  .239845898,  .77754555,
      .330761993,  .197880595,  .748609798,
      .374170051,  .764725435,  .864166871,
      .163879889,  .801515378,  .963314421,
      .880681391,  .616549634,  .858046931,
      .139804533,  .901927794,  .461597104);

      JacobianTestFunction func = new JacobianTestFunction(X,new DMatrixRMaj(NUM_PTS,1));
      System.out.println("func is: " + func);

      DMatrixRMaj numericalJacobian = new DMatrixRMaj(NUM_PTS,3);
      DMatrixRMaj analyticalJacobian = new DMatrixRMaj(NUM_PTS,3);

      alg.configure(func,param.getNumElements());
      alg.computeNumericalJacobian(param,numericalJacobian);
      func.deriv(X,analyticalJacobian);

      //EjmlUnitTests.assertEquals(analyticalJacobian,numericalJacobian,1e-6);

      System.out.println("alg.getFinalCost(): " + alg.getFinalCost());
      System.out.println("alg.getInitialCost(): " + alg.getInitialCost());

      System.out.println("X is: -----------------");
      X.print();

      System.out.println("analyticalJacobian: -----------------");
      analyticalJacobian.print();
      System.out.println("numericalJacobian: -----------------");
      numericalJacobian.print();


      // the number of sample points is equal to the max allowed points
      //runTrivial(NUM_PTS);
      // do the same thing but with a different number of poitns from the max allowed
      //runTrivial(9);


      
    }

    public static void runTrivial( int numPoints ) {
        System.out.println("Runs the simple optimization problem with a set of randomly generated inputs: " + numPoints);
        System.out.println("------------------------------------------------------------------------------------------");

        DMatrixRMaj found = new DMatrixRMaj(9,3);
        DMatrixRMaj expected = new DMatrixRMaj(9,3, true,
         10, -4, 105.2,
         10, -4, 105.2,
         10, -4, 105.2,
         10, -4, 105.2,
         10, -4, 105.2,
         10, -4, 105.2,
         10, -4, 105.2,
         10, -4, 105.2,
         10, -4, 105.2);

        LevenbergMarquardt alg = new LevenbergMarquardt(1e-4);

    //    DMatrixRMaj X = RandomMatrices_DDRM.rectangle(numPoints,1,rand);
        DMatrixRMaj X = new DMatrixRMaj(9,3, true,
        .181024406,  .449489533,  .463171928,
        .375523475,  .659373946,  .689717738,
        .922691834,  .944987968,  .184904894,
        .876445087,  .239845898,  .77754555,
        .330761993,  .197880595,  .748609798,
        .374170051,  .764725435,  .864166871,
        .163879889,  .801515378,  .963314421,
        .880681391,  .616549634,  .858046931,
        .139804533,  .901927794,  .461597104);
            
        DMatrixRMaj Y = new DMatrixRMaj(9,3);
        // compute the observed output given the true praameters
        //new JacobianTestFunction(X,Y).function(expected,Y);
        JacobianTestFunction func = new JacobianTestFunction(X,Y);

        alg.optimize(func,found);
        //alg.optimize(func,X);

        System.out.println("alg.getFinalCost(): " + alg.getFinalCost());
        //System.out.println("expected: -----------------");
        //expected.print();
        System.out.println("X: -----------------");
        X.print();

        System.out.println("Y: -----------------");
        Y.print(); 
        System.out.println(Arrays.toString(Y.getData()));  

        System.out.println("found: -----------------");
        found.print();        
    }    
}
