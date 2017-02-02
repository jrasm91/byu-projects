using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace _312_Project_1__Primality_Tester_
{


    public partial class PrimalityTester : Form {
        private int i = 1;

        public PrimalityTester() {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e) {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.Start();

            string inputNumber = input.Text;
            bool result = isPrime(BigInteger.Parse(inputNumber));
            string error = result? "\t(probablility of error: " + (1 / Math.Pow(2, i)) + ")" : "";
            output.Text = result.ToString() + error;

            stopwatch.Stop();
            time.Text = stopwatch.Elapsed.ToString();
        }

        private bool isPrime(BigInteger n){
            if (n == 0 || n == 1)
                return false;
            Random rand = new System.Random();
            int length = (int)BigInteger.Log(n, 2);
            for (i = 1; i < 20; i++){
                BigInteger random_number = 0;
                while (random_number == 0) {
                    for (int j = 0; j < length; j++)
                        random_number = (random_number << 32) + rand.Next();
                    random_number = random_number % n;
                }
                if (modexp(random_number, n - 1, n) != 1)  // tests if remainder is 1 or not
                    return false;
            }
            return true;
        }

        private BigInteger modexp(BigInteger b, BigInteger p, BigInteger m) {
           BigInteger result = 1;
           while (p > 0) { // iterates through each bit
                if (p % 2 == 1) // if bit is a 1, we include it in computation, otherwise we don't
                    result = (result * b) % m;
                p = p >> 1; // bitshift everything by one, in order to look at next power
                b = (b * b) % m; // since we bitshifted, we raise b to the next power (ie. x^2 -> x^4 -> x^8 -> etc)
           }
           return result;
        }
    }
}
