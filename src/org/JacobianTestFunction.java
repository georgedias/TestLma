package org;

import org.ejml.data.DMatrixRMaj;

/**
 * A very simple function to test how well the numerical jacobian is computed.
 */
public class JacobianTestFunction implements LevenbergMarquardt.ResidualFunction
{
    DMatrixRMaj x;
    DMatrixRMaj y;

    public JacobianTestFunction(DMatrixRMaj x, DMatrixRMaj y) {
        this.x = x;
        this.y = y;
    }

    public void deriv(DMatrixRMaj x, DMatrixRMaj deriv) {
        double dataX[] = x.data;

        int length = x.numRows;

        for( int j = 0; j < length; j++ ) {
            double v = dataX[j];

            double dA = 1;
            double dB = v;
            double dC = v*v;

            deriv.set(j,0,dA);
            deriv.set(j,1,dB);
            deriv.set(j,2,dC);
        }

    }

    public void function( DMatrixRMaj param , DMatrixRMaj y ) {
        double a = param.data[0];
        double b = param.data[1];
        double c = param.data[2];

        int length = x.numRows;

        for( int i = 0; i < length; i++ ) {
            double v = x.data[i];

            y.data[i] = a + b*v + c*v*v;
        }
    }

    @Override
    public void compute(DMatrixRMaj param , DMatrixRMaj residual ) {
        double a = param.data[0];
        double b = param.data[1];
        double c = param.data[2];

        int length = x.numRows;

        for( int i = 0; i < length; i++ ) {
            double v = x.data[i];

            residual.data[i] = a + b*v + c*v*v - y.data[i];
        }
    }

    @Override
    public int numFunctions() {
        return y.getNumElements();
    }
}
