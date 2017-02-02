using System;
using System.Collections;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;
using System.Drawing;

namespace TSP
{
    class ProblemAndSolver
    {
        #region private members
        private const int DEFAULT_SIZE = 25;
        public const int DEFAULT_SEED = -1;

        private const int CITY_ICON_SIZE = 5;

        /// <summary>
        /// how to color various things. 
        /// </summary>
        private Brush cityBrushStartStyle;
        private Brush cityBrushStyle;
        private Pen routePenStyle;


        /// <summary>
        /// keep track of the seed value so that the same sequence of problems can be 
        /// regenerated next time the generator is run. 
        /// </summary>
        private int _seed;
        /// <summary>
        /// number of cities to include in a problem. 
        /// </summary>
        private static int _size;

        /// <summary>
        /// random number generator. 
        /// </summary>
        private Random rnd;
        #endregion

        #region public members.
        public int Size
        {
            get { return _size; }
        }

        public int Seed
        {
            get { return _seed; }
        }
        #endregion

        #region Constructors
        public ProblemAndSolver()
        {
            initialize(DEFAULT_SEED, DEFAULT_SIZE);
        }

        public ProblemAndSolver(int seed)
        {
            initialize(seed, DEFAULT_SIZE);
        }

        public ProblemAndSolver(int seed, int size)
        {
            initialize(seed, size);
        }
        #endregion

        #region Private Methods

        /// <summary>
        /// reset the problem instance. 
        /// </summary>
        private void resetData()
        {
            Cities = new City[_size];
            Route = new ArrayList(_size);
            bssf = null;

            for (int i = 0; i < _size; i++)
                Cities[i] = new City(rnd.NextDouble(), rnd.NextDouble());

            cityBrushStyle = new SolidBrush(Color.Black);
            cityBrushStartStyle = new SolidBrush(Color.Red);
            routePenStyle = new Pen(Color.LightGray, 1);
            routePenStyle.DashStyle = System.Drawing.Drawing2D.DashStyle.Solid;
        }

        private void initialize(int seed, int size)
        {
            this._seed = seed;
            _size = size;
            if (seed != DEFAULT_SEED)
                this.rnd = new Random(seed);
            else
                this.rnd = new Random();
            this.resetData();
        }

        #endregion

        #region Public Methods

        /// <summary>
        /// make a new problem with the given size.
        /// </summary>
        /// <param name="size">number of cities</param>
        public void GenerateProblem(int size)
        {
            _size = size;
            resetData();
        }

        /// <summary>
        /// return a copy of the cities in this problem. 
        /// </summary>
        /// <returns>array of cities</returns>
        public City[] GetCities()
        {
            City[] retCities = new City[_size];
            Array.Copy(Cities, retCities, _size);
            return retCities;
        }

        /// <summary>
        /// draw the cities in the problem.  if the bssf member is defined, then
        /// draw that too. 
        /// </summary>
        /// <param name="g">where to draw the stuff</param>
        public void Draw(Graphics g)
        {
            float width = g.VisibleClipBounds.Width - 45F;
            float height = g.VisibleClipBounds.Height - 15F;
            Font labelFont = new Font("Arial", 10);

            g.DrawString("n(c) means this node is the nth node in the current solution and incurs cost c to travel to the next node.", labelFont, cityBrushStartStyle, new PointF(0F, 0F));

            // Draw lines
            if (bssf != null)
            {
                // make a list of points. 
                Point[] ps = new Point[bssf.Route.Count];
                int index = 0;
                foreach (City c in bssf.Route)
                {
                    if (index < bssf.Route.Count - 1)
                        g.DrawString(" " + index + "(" + c.costToGetTo(bssf.Route[index + 1] as City) + ")", labelFont, cityBrushStartStyle, new PointF((float)c.X * width + 3F, (float)c.Y * height));
                    else
                        g.DrawString(" " + index + "(" + c.costToGetTo(bssf.Route[0] as City) + ")", labelFont, cityBrushStartStyle, new PointF((float)c.X * width + 3F, (float)c.Y * height));
                    ps[index++] = new Point((int)(c.X * width) + CITY_ICON_SIZE / 2, (int)(c.Y * height) + CITY_ICON_SIZE / 2);
                }

                if (ps.Length > 0)
                {
                    g.DrawLines(routePenStyle, ps);
                    g.FillEllipse(cityBrushStartStyle, (float)Cities[0].X * width - 1, (float)Cities[0].Y * height - 1, CITY_ICON_SIZE + 2, CITY_ICON_SIZE + 2);
                }

                // draw the last line. 
                g.DrawLine(routePenStyle, ps[0], ps[ps.Length - 1]);
            }

            // Draw city dots
            foreach (City c in Cities)
            {
                g.FillEllipse(cityBrushStyle, (float)c.X * width, (float)c.Y * height, CITY_ICON_SIZE, CITY_ICON_SIZE);
            }

        }

        #endregion

        private class TSPSolution
        {
            public ArrayList Route;

            public TSPSolution(ArrayList iroute)
            {
                Route = new ArrayList(iroute);
            }
            public TSPSolution(City firstcity)
            {
                Route = new ArrayList();
                Route.Add(firstcity);
            }

            public double costOfRoute()
            {
                // go through each edge in the route and add up the cost. 
                double cost = 0D;
                for (int x = 0; x < Route.Count - 1; x++)
                    cost += ((City)(Route[x])).costToGetTo((City)Route[x + 1]);
                return cost;
            }

            public List<TSPSolution> makeChildren()
            {
                List<TSPSolution> result = new List<TSPSolution>();
                if (Route.Count == _size)
                {
                    TSPSolution newSolution = new TSPSolution(new ArrayList(Route.ToArray()));
                    newSolution.Route.Add(Cities[0]);
                    result.Add(newSolution);
                    return result;
                }

                for (int i = 0; i < _size; i++)
                {
                    if (Route.Contains(Cities[i]))
                        continue;
                    TSPSolution newSolution = new TSPSolution(new ArrayList(Route.ToArray()));
                    newSolution.Route.Add(Cities[i]);
                    result.Add(newSolution);
                }
                return result;
            }

            public Boolean isCrossed()
            {
                if (Route.Count < 4) return false;
                City end1 = Route[Route.Count - 1] as City;
                City end2 = Route[Route.Count - 2] as City;
                for (int i = 0; i < Route.Count - 1; i++)
                {
                    City start1 = Route[i] as City;
                    City start2 = Route[i + 1] as City;
                    if (isCrossedMain(
                            start1.X, start1.Y, start2.X, start2.Y,
                        end1.X, end1.Y, end2.X, end2.Y))
                        return true;
                }
                return false;
            }

            public double getBound()
            {
                double cost = costOfRoute();
                for (int i = 0; i < Cities.Length; i++)
                {
                    if (!Route.Contains((City)Cities[i]))
                        cost += costMinMatrix[i];
                }
                return cost;
            }

            public Boolean isSolution()
            {
                return Route.Count == Cities.Length + 1;
            }
        }

        private static City[] Cities;
        private static ArrayList Route;
        private static TSPSolution bssf;
        private Stack<TSPSolution> stack;
        private static double[,] costMatrix;
        private static double[] costMinMatrix;
        private Stopwatch timer;
        private int counter, max;

        public void solveProblem()
        {
            timer = new Stopwatch();
            timer.Start();
            counter = 0;
            max = 0;

            Console.Out.WriteLine();
            initializeSolver();
            stack.Push(new TSPSolution(Cities[0]));

            bool debug = true;
            while (stack.Count != 0 && (timer.Elapsed.TotalSeconds <= 20 || debug))
            {
                counter++;
                if (stack.Count > max) max = stack.Count;
                updateUI();
                TSPSolution parent = stack.Pop();
                if (parent.getBound() >= bssf.costOfRoute())
                    continue;
                //if (parent.isCrossed())
                //    continue;
                List<TSPSolution> children = parent.makeChildren();
                foreach (TSPSolution child in children)
                {
                    if (child.getBound() < bssf.costOfRoute())
                    {
                        if (child.isSolution())
                        {
                            Console.Out.WriteLine("Solution: {0}", child.costOfRoute());
                            bssf = child;
                        }
                        else
                            stack.Push(child);

                    }
                    // else discard it (don't explore)
                }
            }

            timer.Stop();
            updateUI();
        }

        private void initializeSolver()
        {
            stack = new Stack<TSPSolution>(Cities.Length * 2);

            costMatrix = new double[Cities.Length, Cities.Length];
            costMinMatrix = new double[Cities.Length];

            initializeCostMatrix();
            initializeBSSF();
        }

        private void initializeCostMatrix()
        {
            for (int i = 0; i < Cities.Length; i++)
            {
                costMinMatrix[i] = Int16.MaxValue;
                for (int j = 0; j < Cities.Length; j++)
                {
                    double cost = Cities[i].costToGetTo(Cities[j]);
                    costMatrix[i, j] = cost;
                    if (i != j && cost < costMinMatrix[i])
                        costMinMatrix[i] = cost;
                }
            }
        }

        private void initializeBSSF()
        {
            Route = new ArrayList();
            for (int x = 0; x < Cities.Length; x++)
                Route.Add(Cities[x]);
            Route.Add(Cities[0]);
            bssf = new TSPSolution(Route);
        }


        private void updateUI()
        {

            Program.MainForm.tbCostOfTour.Text = " " + bssf.costOfRoute();
            Program.MainForm.tbTime.Text = " " + timer.Elapsed.TotalSeconds.ToString();
            Program.MainForm.tbSize1.Text = " " + stack.Count;
            Program.MainForm.tbSize2.Text = " " + counter;
            Program.MainForm.tbSize3.Text = " " + max;
            Program.MainForm.Update();
            Program.MainForm.Invalidate();
        }

        public static Boolean isCrossedMain(
            double sx1, double sy1, double sx2, double sy2,
            double ex1, double ey1, double ex2, double ey2)
        {
            double m1 = (sy1 - sy2) / (sx1 - sx2);
            double b1 = -m1 * sx1 + sy1;

            double m2 = (ey1 - ey2) / (ex1 - ex2);
            double b2 = -m2 * ex1 + ey1;

            if (m1 == m2)
                return false;
            
            double x = (b2 - b1) / (m1 - m2);

            if (sx1 > sx2)
            {
                double temp = sx1;
                sx1 = sx2;
                sx2 = temp;
            }

            if (ex1 > ex2)
            {
                double temp = ex1;
                ex1 = ex2;
                ex2 = temp;
            }

            if (x < sx1 || x > sx2)
                return false;
            if (x < ex1 || x > ex2)
                return false;
            return true;
        }
    }
}

        