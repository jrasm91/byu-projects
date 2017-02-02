using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.Diagnostics;

namespace TSP
{
    class ProblemAndSolver
    {
        private class TSPSolution
        {
            /// <summary>
            /// we use the representation [cityB,cityA,cityC] 
            /// to mean that cityB is the first city in the solution, cityA is the second, cityC is the third 
            /// and the edge from cityC to cityB is the final edge in the path.  
            /// you are, of course, free to use a different representation if it would be more convenient or efficient 
            /// for your node data structure and search algorithm. 
            /// </summary>
            public ArrayList 
                Route;

            public TSPSolution(ArrayList iroute)
            {
                Route = new ArrayList(iroute);
            }


            /// <summary>
            ///  compute the cost of the current route.  does not check that the route is complete, btw.
            /// assumes that the route passes from the last city back to the first city. 
            /// </summary>
            /// <returns></returns>
            public double costOfRoute()
            {
                // go through each edge in the route and add up the cost. 
                int x;
                City here; 
                double cost = 0D;
                
                for (x = 0; x < Route.Count-1; x++)
                {
                    here = Route[x] as City;
                    cost += here.costToGetTo(Route[x + 1] as City);
                }
                // go from the last city to the first. 
                here = Route[Route.Count - 1] as City;
                cost += here.costToGetTo(Route[0] as City);
                return cost; 
            }
        }

        #region private members
        private const int DEFAULT_SIZE = 25;
        
        private const int CITY_ICON_SIZE = 5;

        /// <summary>
        /// the cities in the current problem.
        /// </summary>
        private City[] Cities;
        /// <summary>
        /// a route through the current problem, useful as a temporary variable. 
        /// </summary>
        private ArrayList Route;
        /// <summary>
        /// best solution so far. 
        /// </summary>
        private TSPSolution bssf; 

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
        private int _size;

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

        public const int DEFAULT_SEED = -1;

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
            routePenStyle = new Pen(Color.LightGray,1);
            routePenStyle.DashStyle = System.Drawing.Drawing2D.DashStyle.Solid;
        }

        private void initialize(int seed, int size)
        {
            this._seed = seed;
            this._size = size;
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
            this._size = size;
            resetData(); 
        }

        /// <summary>
        /// return a copy of the cities in this problem. 
        /// </summary>
        /// <returns>array of cities</returns>
        public City[] GetCities()
        {
            City[] retCities = new City[Cities.Length];
            Array.Copy(Cities, retCities, Cities.Length);
            return retCities;
        }

        /// <summary>
        /// draw the cities in the problem.  if the bssf member is defined, then
        /// draw that too. 
        /// </summary>
        /// <param name="g">where to draw the stuff</param>
        public void Draw(Graphics g)
        {
            float width  = g.VisibleClipBounds.Width-45F;
            float height = g.VisibleClipBounds.Height-15F;
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
                    if (index < bssf.Route.Count -1)
                        g.DrawString(" " + index +"("+c.costToGetTo(bssf.Route[index+1]as City)+")", labelFont, cityBrushStartStyle, new PointF((float)c.X * width + 3F, (float)c.Y * height));
                    else 
                        g.DrawString(" " + index +"("+c.costToGetTo(bssf.Route[0]as City)+")", labelFont, cityBrushStartStyle, new PointF((float)c.X * width + 3F, (float)c.Y * height));
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


        private Stopwatch stopwatch;
        private PriorityQueue<double, State> agenda;
        int maxSize;
        /// <summary>
        ///  solve the problem.  This is the entry point for the solver when the run button is clicked
        /// right now it just picks a simple solution. 
        /// </summary>
        public void solveProblem()
        {
            stopwatch = new Stopwatch();
            stopwatch.Start();
            bssf = generateInitialBSSF();
            State s = initState();
            agenda = new PriorityQueue<double, State>();
            agenda.Add(new KeyValuePair<double, State>(s.boundValue / s.path.Count, s));

            maxSize = 0;
            while (!agenda.IsEmpty && stopwatch.ElapsedMilliseconds < 60000 && bssf.costOfRoute() != s.boundValue)
            {
                if (agenda.Count > maxSize) maxSize = agenda.Count;
                State u = agenda.Dequeue().Value;
                if (u.boundValue < bssf.costOfRoute())
                {
                    if (stopwatch.ElapsedMilliseconds % 5000 == 0) updateUI();
                    ArrayList children = generateChildren(u);
                    foreach (State w in children)
                    {
                        if (w.boundValue < bssf.costOfRoute())
                        {
                            if (w.path.Count == Cities.Length)
                            {
                                bssf = bssfFromIntArray(w.path);
                                myBssf = (int[])w.path.ToArray(System.Type.GetType("System.Int32"));
                                while (updateBssf(myBssf)) ;
                                updateUI();
                            }
                            else
                                agenda.Add(new KeyValuePair<double, State>(w.boundValue / w.path.Count, w));
                        }
                    }
                }
            }
            updateUI();
        }

        private TSPSolution bssfFromIntArray(ArrayList intArray)
        {
            Route = new ArrayList();
            foreach (int i in intArray)
                Route.Add(Cities[i]);
            bssf = new TSPSolution(Route);
            return bssf;
        }


        private ArrayList generateChildren(State u)
        {
            ArrayList children = new ArrayList();
            int row = (int)u.path[(u.path.Count-1)];
            for (int i = 0; i < Cities.Length; i++)
            {
                if (u.matrix[row, i] != double.MaxValue && !u.path.Contains(i))
                {
                    State child = new State(u);
                    child.path.Add(i);
                    child.boundValue += child.matrix[row, i];
                    for (int j = 0; j < Cities.Length; j++)
                    {
                        child.matrix[row, j] = double.MaxValue;
                        child.matrix[j, i] = double.MaxValue;
                    }
                    child.calculateBound();
                    children.Add(child);
                }
            }
            return children;
        }

        private State initState()
        {
            State s = new State();
            s.matrix = new double[Cities.Length, Cities.Length];
            for (int i = 0; i < Cities.Length; i++)
            {
                for (int j = 0; j < Cities.Length; j++)
                {
                    double entry = Cities[i].costToGetTo(Cities[j]);
                    if (entry == 0)
                        entry = double.MaxValue;
                    s.matrix[i, j] = entry;
                }
            }
            s.calculateBound();
            return s;
        }

        int[] myBssf; // used for local mins

        // generates an initial bssf by using local search algorithm 4 times and picking the best result
        private TSPSolution generateInitialBSSF()
        {
            // initialize starting solutions
            myBssf = myInitializeBSSF();
            bssf = bssfFromIntArray(new ArrayList(myBssf));
            int count = 0;

            while (count < 4)
            {
                count++;
                myBssf = myInitializeBSSF();
                while (updateBssf(myBssf)) // if local Bssf was updated, re-run
                    updateUI();
                //Console.Out.WriteLine("Cost: {0}", costOfRoute(myBssf));
            }
            updateUI();
            return bssf;
        }

        // generates a random initial bssf
        private int[] myInitializeBSSF()
        {
            List<int> indexes = new List<int>();
            for (int i = 0; i < Cities.Length; i++)
                indexes.Add(i);

            int[] newBssf = new int[_size];

            for (int i = 0; i < Cities.Length; i++)
            {
                int nextIndex = indexes[new Random().Next(indexes.Count)];
                newBssf[i] = nextIndex;
                indexes.Remove(nextIndex);
            }
            return newBssf;
        }

        // calculates the cost of the route given a route
        private double costOfRoute(int[] route)
        {
            double cost = 0D;
            for (int i = 0; i < _size; i++)
                cost += Cities[route[i]].costToGetTo(Cities[route[(i + 1) % _size]]);
            return cost;
        }


        // performs local search (switches two points in the solution) and searches for better result
        private Boolean updateBssf(int[] route)
        {
            Boolean result = false;
            for (int i = 0; i < _size; i++)
                for (int j = i + 1; j < _size; j++)
                {
                    int[] child = new int[route.Length];
                    Array.Copy(route, child, _size);
                    child[i] = route[j]; //switch two points
                    child[j] = route[i];
                    // check if child is a new local/global max
                    if (costOfRoute(child) < costOfRoute(myBssf))
                    { 
                        myBssf = child;
                        if (costOfRoute(myBssf) < bssf.costOfRoute())
                            bssf = bssfFromIntArray(new ArrayList(myBssf));
                        result = true;
                    }
                }
            return result;
        }

        // udpates the gui
        private void updateUI()
        {
            String size = (agenda == null) ? "0" : "" + agenda.Count;
            Program.MainForm.tbCostOfTour.Text = " " + bssf.costOfRoute();
            Program.MainForm.tbElapsedTime.Text = " " + stopwatch.Elapsed.TotalSeconds.ToString();
            Program.MainForm.tbCurrentSize.Text = " " + size;
            Program.MainForm.tbMaxSize.Text = " " + maxSize;
            Program.MainForm.Update();
            Program.MainForm.Invalidate();
        }

        class State
        {
            public ArrayList path;
            public double[,] matrix;
            public double boundValue;

            public State()
            {
                this.path = new ArrayList();
                this.path.Add(0);
                this.matrix = null;
                this.boundValue = 0;
            }

            public State(State m)
            {
                this.path = (ArrayList) m.path.Clone();
                this.matrix = (double[,]) m.matrix.Clone();
                this.boundValue = m.boundValue;
            }

            internal void calculateBound()
            {
                double bound = boundValue;
                // reduce rows
                for (int i = 0; i < matrix.GetLength(1); i++)
                {
                    double minInRow = matrix[i, 0];
                    for (int j = 0; j < matrix.GetLength(1); j++)
                    {
                        if (matrix[i, j] < minInRow)
                            minInRow = matrix[i, j];
                    }
                    if (minInRow < double.MaxValue)
                    {
                        bound += minInRow;
                        for (int j = 0; j < matrix.GetLength(1); j++)
                        {
                            if (matrix[i, j] != double.MaxValue)
                                matrix[i, j] = matrix[i, j] - minInRow;
                        }
                    }
                }
                // reduce columns
                for (int i = 0; i < matrix.GetLength(1); i++)
                {
                    bool colHasZero = false;
                    double minInCol = matrix[0, i];
                    for (int j = 0; j < matrix.GetLength(1); j++)
                    {
                        if (matrix[j, i] == 0)
                            colHasZero = true;
                        if (matrix[j, i] < minInCol)
                            minInCol = matrix[j, i];
                    }
                    if (colHasZero == false)
                    {
                        if (minInCol < double.MaxValue)
                        {
                            bound += minInCol;
                            for (int j = 0; j < matrix.GetLength(1); j++)
                            {
                                if (matrix[j, i] != double.MaxValue)
                                    matrix[j, i] = matrix[j, i] - minInCol;
                            }
                        }
                    }
                }
                boundValue = bound;
            }

        }
        #endregion
    }
}
