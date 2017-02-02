using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;

namespace _2_convex_hull
{
    class ConvexHullSolver
    {
        public void Solve(System.Drawing.Graphics g, List<PointF> pointList, PictureBox pictureBoxView)
        {
            pointList.Sort((p1, p2) => (p1.X.CompareTo(p2.X)));
            PointF[] pointArray = solveConvexHull(pointList).ToArray();
            g.DrawPolygon(new Pen(Color.Blue), pointArray);
        }
        private List<PointF> solveConvexHull(List<PointF> subList)
        {
            if (subList.Count == 1)
                return subList; //base case
            else
            {
                List<PointF> listLeft = new List<PointF>();
                List<PointF> listRight = new List<PointF>();

                // divides list into two sections based off of x-coordinates
                for (int i = 0; i < subList.Count; i++)
                {
                    if (i < subList.Count / 2)
                        listLeft.Add(subList[i]);
                    else
                        listRight.Add(subList[i]);
                }

                // recursive call and merge Hulls
                listLeft = solveConvexHull(listLeft);
                listRight = solveConvexHull(listRight);

                return combineLists(listLeft, listRight);
            }
        }

        private List<PointF> combineLists(List<PointF> listLeft, List<PointF> listRight)
        {
            // use right-most and left-most points to initiate initial tangent lines
            int rightIndex = findLeftMostIndex(listRight);
            int leftIndex =  findRightMostIndex(listLeft);

            // find top tangent line
            int rightTop = rightIndex;
            int leftTop = leftIndex;

            Boolean upperTangent = false;
            while (!upperTangent)
            {
                upperTangent = true;
                // this loop test all possible leftList points with current rightPoint
                for (int i = 0; i < listLeft.Count; i++)
                {
                    if (leftTop == i)
                        continue;
                    if (tangentTest(listRight[rightTop], listLeft[leftTop], listLeft[i]) >= 0)
                    {
                        leftTop = i;
                        upperTangent = false;
                    }
                }
                // this loop tests all possible right points with current left point
                for (int i = 0; i < listRight.Count; i++)
                {
                    if (rightTop == i)
                        continue;
                    if (tangentTest(listLeft[leftTop], listRight[rightTop], listRight[i]) >= 0)
                    {
                        rightTop = i;
                        upperTangent = false;
                    }
                }
            }

            // same thing as above, but for lower tangent
            int rightBottom = rightIndex;
            int leftBottom = leftIndex;

            Boolean lowerTangent = false;
            while (!lowerTangent)
            {
                lowerTangent = true;
                for (int i = 0; i < listLeft.Count; i++)
                {
                    if (leftBottom == i)
                        continue;
                    if (tangentTest(listRight[rightBottom], listLeft[leftBottom], listLeft[i]) <= 0)
                    {
                        leftBottom = i;
                        lowerTangent = false;
                    }
                }
                for (int i = 0; i < listRight.Count; i++)
                {
                    if (rightBottom == i)
                        continue;
                    if (tangentTest(listLeft[leftBottom], listRight[rightBottom], listRight[i]) <= 0)
                    {
                        rightBottom = i;
                        lowerTangent = false;
                    }
                }
            }

            //takes right half of hull (start with top half), then left half of hull (now starting with the bottom) and put them in one list
            List<PointF> rightPoints = getPoints(rightTop, rightBottom, listRight);
            List<PointF> leftPoints = getPoints(leftBottom, leftTop, listLeft); 
            List<PointF> result = new List<PointF>();
            result.AddRange(rightPoints);
            result.AddRange(leftPoints);
            return result;
        }
        // finds the left-most point (based off x-coord) in a list
        private int findLeftMostIndex(List<PointF> list)
        {
            int leftIndex = 0;
            for (int i = 0; i < list.Count; i++)
                if (list[i].X < list[leftIndex].X) leftIndex = i;
            return leftIndex;
        }
        // same as above, but right-most
        private int findRightMostIndex(List<PointF> list)
        {
            int rightIndex = 0;
            for (int i = 0; i < list.Count; i++)
                if (list[i].X > list[rightIndex].X) rightIndex = i;
            return rightIndex;
        }
        // returns negative number for point (r), above pq, positive if below
        private double isCrossed(PointF p, PointF q, PointF testPoint)
        {
            double m = (p.Y - q.Y)/(p.X - q.X);
            double b = -m * p.X + p.Y;
            return 1.0 * m * testPoint.X + b - testPoint.Y;
        } 
        // returns the list of points between the two indicated indexes (maybe loop around to beginning if the second index is less than the first
        private List<PointF> getPoints(int index1, int index2, List<PointF> list)
        {
            List<PointF> result = new List<PointF>();
            if (index1 <= index2)
                for (int i = index1; i <= index2; i++) //add top point down to min on right
                    result.Add(list[i]);
            else
            {
                for (int i = index1; i < list.Count; i++) //add top point down to min on right
                    result.Add(list[i]);
                for (int i = 0; i <= index2; i++) //add top point down to min on right
                    result.Add(list[i]);
            }
            return result;
        }
    }
}
