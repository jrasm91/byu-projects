using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace _312_Project_6__Linear_Programming_
{
    public partial class Simplex : Form
    {
        private double[] b;
        private double[] c;
        private double[,] A;
        private List<int> B;
        private List<int> N;
        private double v;

        public Simplex()
        {
            InitializeComponent();
            InitializeVariables();
        }

        // initializes all of the initial vectors
        private void InitializeVariables()
        {
            A = new double[9, 9]
            { 
                {0,     0,      0,      0,      0,      0,      0,      0,      0},
                {0,     0,      0,      0,      0,      0,      0,      0,      0},
                {0,     0,      0,      0,      0,      0,      0,      0,      0},
                {0,     0,      0,      0,      0,      0,      0,      0,      0},
                {1,     1,      1,      1,      0,      0,      0,      0,      0},
                {300,   30,     57,     12,     0,      0,      0,      0,      0},
                {10000, 12000,  12300,  9100,   0,      0,      0,      0,      0},
                {500,   40,     63,     10,     0,      0,      0,      0,      0},
                {40,    12,     57,     30,     0,      0,      0,      0,      0}
            };
            b = new double[9] { 0, 0, 0, 0, 2000, 1000, 1000000, 640, 432 };
            c = new double[9] { 102, 844.6, 20.73, 853.2, 0, 0, 0, 0, 0 };
            B = new List<int>(new int[5] { 4, 5, 6, 7, 8 });
            N = new List<int>(new int[4] { 0, 1, 2, 3 });
            v = 0;
        }

        // outprints the strings at the end to display the results
        private void solve_click(object sender, EventArgs e)
        {
            double[] results = solve();
            String format = "#00.00";
            String output = "\r\nRESULTS";
            String copper = String.Format("\r\nCopper: \t{0} ounces", results[0].ToString(format));
            String gold = String.Format("\r\nGold: \t{0} ounces", results[1].ToString(format));
            String silver = String.Format("\r\nSilver: \t{0} ounces", results[2].ToString(format));
            String platinum = String.Format("\r\nPlatinum: \t{0} ounces", results[3].ToString(format));
            String total = String.Format("\r\nTotal: ${0}", v.ToString(format));
            textBox1.Text = output + copper + gold + silver + platinum + total;
        }

        // solve functions that computes the fina results
        private double[] solve()
        {
            int e;
            int l;
            while (true)
            {
                // find next entering variable
                e = findE();
                // if no more entering variables, we are finished
                if (e == -1)
                    break;
                // find leaving variable
                l = findL(e);
                // with entering variable and leaving variable perform pivot and repeat process
                pivot(e, l);
            }

            // after all the pivots are done set c to be the final amounts for each material
            for (int i = 0; i < b.Count(); i++)
            {
                if (B.Contains(i))
                    c[i] = b[i];
                else
                    c[i] = 0;
            }
            return c;
        }

        // searches through all indexes in vector N for one that qualifies as "entering"
        private int findE()
        {
            foreach (int j in N)
                if (c[j] > 0)
                    return j;
            return -1;
        }

        // searches through all indexes in vector B for one that qualifies as "leaving" (ratio check)
        private int findL(int e)
        {
            int minIndex = -1;
            foreach (int i in B)
            {
                if (A[i, e] > 0)
                {
                    if (minIndex == -1)
                        minIndex = i;
                    else if (b[i] / A[i, e] < b[minIndex] / A[minIndex, e])
                        minIndex = i;
                }
            }
            return minIndex;
        }

        // updates all of the vectors with the entering index e and the leaving index l
        private void pivot(int e, int l)
        {
            // update original b list
            b[e] = b[l] / A[l, e];
            // update N list
            foreach (int j in N)
            {
                if (j == e) continue;
                A[e, j] = A[l, j] / A[l, e];
            }
            A[e, l] = 1 / A[l, e];

            // update B list
            foreach (int i in B)
            {
                if (i == l) continue;
                b[i] = b[i] - A[i, e] * b[e];
                foreach (int j in N)
                {
                    if (j == e) continue;
                    A[i, j] = A[i, j] - (A[i, e] * A[e, j]);
                }
                A[i, l] = -1 * A[i, e] * A[e, l];
            }

            // compute objective function
            v = v + c[e] * b[e];
            foreach (int j in N)
            {
                if (j == e) continue;
                c[j] = c[j] - c[e] * A[e, j];
            }
            c[l] = -1 * c[e] * A[e, l];

            // compute new basic/non-basic variables sets
            N.Remove(e);
            N.Add(l);
            B.Remove(l);
            B.Add(e);
        }
    }
}
