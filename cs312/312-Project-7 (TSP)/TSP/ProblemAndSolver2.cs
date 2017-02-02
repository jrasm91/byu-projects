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
            cities = new City[_size];
            bssf = null;

            for (int i = 0; i < _size; i++)
                cities[i] = new City(rnd.NextDouble(), rnd.NextDouble());

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
            Array.Copy(cities, retCities, _size);
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
                Point[] ps = new Point[bssf.Length];

                for (int i = 0; i < _size; i++)
                {
                    g.DrawString(" " + i + "(" +
                        cities[bssf[i]].costToGetTo(cities[bssf[(i + 1) % _size]]) + ")",
                        labelFont,
                        cityBrushStartStyle,
                        new PointF((float)cities[bssf[i]].X * width + 3F, (float)cities[bssf[i]].Y * height));
                    ps[i] =
                        new Point(
                            (int)(cities[bssf[i]].X * width) + CITY_ICON_SIZE / 2,
                            (int)(cities[bssf[i]].Y * height) + CITY_ICON_SIZE / 2);
                }

                if (ps.Length > 0)
                {
                    g.DrawLines(routePenStyle, ps);
                    g.FillEllipse(cityBrushStartStyle, (float)cities[0].X * width - 1, (float)cities[0].Y * height - 1, CITY_ICON_SIZE + 2, CITY_ICON_SIZE + 2);
                }

                g.DrawLine(routePenStyle, ps[0], ps[ps.Length - 1]);
            }

            foreach (City c in cities)
                g.FillEllipse(cityBrushStyle, (float)c.X * width, (float)c.Y * height, CITY_ICON_SIZE, CITY_ICON_SIZE);

        }

        #endregion

        private City[] cities;
        private int[] bssf;
        private SortedSet<Node> bssfs;
        private Queue<int[]> list;
        private Stopwatch timer;
        private int counter, max;
        private Boolean debug = false;
        private HashSet<int[]> explored;

        private class Node : IComparable<Node>
        {
            public double cost;
            public int[] route;

            public Node(double cost, int[] route)
            {
                this.cost = cost;
                this.route = route;

            }
            public int CompareTo(Node otherNode)
            {
                if (otherNode == null) return 1;
                int result = this.route.Length.CompareTo(otherNode.route.Length);
                if (result != 0)
                    return this.route.Length;
                else
                    return this.cost.CompareTo(otherNode.cost);
            }
        }

        public void solveProblem()
        {
            timer = new Stopwatch();
            timer.Start();
            list = new Queue<int[]>();
            explored = new HashSet<int[]>();
            bssfs = new SortedSet<Node>();
            counter = 0;
            max = 0;
            bssf = initializeBSSF();

            list.Enqueue(bssf);
            bssfs.Add(new Node(costOfRoute(bssf), bssf));

            while (timer.Elapsed.TotalSeconds <= _size / 10 || debug)
            {
                if (list.Count == 0)
                {
                    bssfs.Add(new Node(costOfRoute(bssf), bssf));
                    bssf = initializeBSSF();
                    list.Enqueue(bssf);
                }
                counter++;
                if (list.Count > max) max = list.Count;
                //updateUI();

                int[] parentRoute = list.Dequeue();
                if (updateBssf1(parentRoute))
                {
                    list.Enqueue(bssf);
                    updateUI();
                }
                //if(updateBssf2(parentRoute))
                //    list.Enqueue(bssf);
            }

            bssf = bssfs.Min.route;
            timer.Stop();
            updateUI();
        }

        private int[] initializeBSSF()
        {
            List<int> indexes = new List<int>();
            for (int i = 0; i < cities.Length; i++)
                indexes.Add(i);

            int[] newBssf = new int[_size];

            for (int i = 0; i < cities.Length; i++)
            {
                int nextIndex = indexes[new Random().Next(indexes.Count)];
                newBssf[i] = nextIndex;
                indexes.Remove(nextIndex);
            }
            return newBssf;
        }

        private double costOfRoute(int[] route)
        {
            double cost = 0D;
            for (int i = 0; i < _size; i++)
                cost += cities[route[i]].costToGetTo(cities[route[(i + 1) % _size]]);
            return cost;
        }

        private Boolean updateBssf2(int[] route)
        {
            int[] temp = bssf;
            Boolean result = false;

            for (int i = 0; i < _size; i++)
            {
                for (int j = i + 1; j < _size; j++)
                {
                    for (int k = j + 1; k < _size; k++)
                    {
                        int[] child = new int[route.Length];
                        Array.Copy(route, child, _size);
                        child[i] = route[j];
                        child[j] = route[k];
                        child[k] = route[i];
                        double childCost = costOfRoute(child);
                        double bssfCost = costOfRoute(bssf);
                        if (childCost < bssfCost)
                        {
                            //Console.Out.WriteLine(
                            //    "Solution: {0} \t\tCount: {1}\t\tCities: {2}", 
                            //    costOfRoute(child), counter, printArray(child));
                            bssf = child;
                            result = true;
                            updateUI();
                        }
                        //else if ((childCost - bssfCost) * 100 <= 500 &&
                        //    !explored.Contains(child))
                        //{
                        //    Console.Out.WriteLine("true");
                        //    updateBssf(child);
                        //}
                        //explored.Add(child);
                    }
                }
            }
            return result;
        }

        private Boolean updateBssf1(int[] route)
        {
            int[] temp = bssf;
            Boolean result = false;

            for (int i = 0; i < _size; i++)
            {
                for (int j = i + 1; j < _size; j++)
                {
                    int[] child = new int[route.Length];
                    Array.Copy(route, child, _size);
                    child[i] = route[j];
                    child[j] = route[i];
                    double childCost = costOfRoute(child);
                    double bssfCost = costOfRoute(bssf);
                    if (childCost < bssfCost)
                    {
                        bssf = child;
                        result = true;
                    }
                }
            }
            return result;
        }

        private Boolean isCrossed(int[] route)
        {
            if (route.Length < 4) return false;
            City end1 = cities[route.Length - 1] as City;
            City end2 = cities[route.Length - 2] as City;
            for (int i = 0; i < route.Length - 1; i++)
            {
                City start1 = cities[route[i]] as City;
                City start2 = cities[route[i + 1]] as City;
                if (isCrossed(
                        start1.X, start1.Y, start2.X, start2.Y,
                    end1.X, end1.Y, end2.X, end2.Y))
                    return true;
            }
            return false;
        }

        private String printArray(int[] route)
        {
            String buf = "";
            for (int i = 0; i < route.Length; i++)
                buf += String.Format("{0}-->{1}:{2}, ", i, i + 1, "" + ((City)cities[route[i]]).costToGetTo(cities[route[(i + 1) % route.Length]]));
            return buf;

        }

        private static Boolean isCrossed(
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

        private void updateUI()
        {
            Program.MainForm.tbCostOfTour.Text = " " + bssfs.Min.cost;
            Program.MainForm.tbTime.Text = " " + timer.Elapsed.TotalSeconds.ToString();
            Program.MainForm.tbSize1.Text = " " + list.Count;
            Program.MainForm.tbSize2.Text = " " + counter;
            Program.MainForm.tbSize3.Text = " " + max;
            Program.MainForm.Update();
            Program.MainForm.Invalidate();
        }
    }
}

