using System;
using System.Collections.Generic;
using System.Text;

namespace GeneticsLab
{
    class PairWiseAlign
    {
        private int MaxCharactersToAlign = 5000; 
        private int INDEL = 5;
        private int SUB = 1;
        private int MATCH = -3;
        public Char SUB_CHAR = '?';
        public Char INDEL_CHAR = 'I';
        public Char MATCH_CHAR = '|';

        private Char[] charA, charB;
        private int[] resultRow;

        private void initialize(GeneSequence sequenceA, GeneSequence sequenceB)
        {
            // grab first 5000 (or all if less than 5000) characters of each sequence
            charA = formatSequence(sequenceA.Sequence, MaxCharactersToAlign).ToCharArray();
            charB = formatSequence(sequenceB.Sequence, MaxCharactersToAlign).ToCharArray();
            resultRow = new int[charB.Length + 1];
            // initialize bottom row with costs for INDEL
            for (int i = 0; i < resultRow.Length; i++)
                resultRow[i] = i * INDEL;
        }

        public int Align(GeneSequence sequenceA, GeneSequence sequenceB)
        {
            // initialize arrays and strings and first row
            initialize(sequenceA, sequenceB);

            // calculate each additional row 
            for (int i = 0; i < charA.Length; i++)
                resultRow = computeNextRow(resultRow, charA[i], charB);

            // return score
            return resultRow[resultRow.Length - 1];
        }

        public String[] extractSolution(GeneSequence sequenceA, GeneSequence sequenceB)
        {
            // initialize arrays and strings and first row
            initialize(sequenceA, sequenceB);

            // initialize table to store each row
            List<int[]> resultTable = new List<int[]>(charA.Length + 1);
            resultTable.Add(resultRow);

            // calculate each additional row (and save it for backtrace)
            for (int i = 0; i < charA.Length; i++)
                resultTable.Add(computeNextRow(resultTable[i], charA[i], charB));

            // compute back trace and generate final strings
            StringBuilder buildA = new StringBuilder();
            StringBuilder buildB = new StringBuilder();
            StringBuilder buildC = new StringBuilder();
            int row = charA.Length;
            int col = charB.Length;

            // while index pointers to string a (row) and string b (column) aren't at zero figure out last operation
            while (row != 0 || col != 0)
            {
                if (resultTable[row][col] == resultTable[row][col - 1] + INDEL)
                {
                    buildA.Append('-');
                    buildB.Append(charB[--col]);
                    buildC.Append(INDEL_CHAR);
                }
                else if (resultTable[row][col] == resultTable[row - 1][col] + INDEL)
                {
                    buildA.Append(charA[--row]);
                    buildB.Append('-');
                    buildC.Append(INDEL_CHAR);
                }
                else if (resultTable[row][col] == resultTable[row - 1][col - 1] + MATCH ||
                    resultTable[row][col] == resultTable[row - 1][col - 1] + SUB)
                {
                    buildA.Append(charA[--row]);
                    buildB.Append(charB[--col]);
                    buildC.Append(charB[col] == charA[row] ? MATCH_CHAR : SUB_CHAR);
                
                }
                else
                    throw new ArgumentException();
            }

            String[] results = new String[3];
            results[0] = reverseString(buildA.ToString());
            results[1] = reverseString(buildB.ToString());
            results[2] = reverseString(buildC.ToString());
            return results;
        }

        private String reverseString(String s)
        {
            Char[] charArray = s.ToCharArray();
            Array.Reverse(charArray);
            return new String(charArray);
        }


        // given a row of scores, a row of characters, and a comparison character, this method
        // returns the new row of scores
        public int[] computeNextRow(int[] bottomRow, Char currentCharA, Char[] charB)
        {
            int[] topScores = new int[bottomRow.Length];
            topScores[0] = bottomRow[0] + INDEL; // each row[0] element goes up by the cost of an indel
            for (int j = 1; j < topScores.Length; j++)
            {
                // the new score s calculated by comparing the three values (INDEL(Left), INDEL(Bottom), and SUB/MATCH)
                topScores[j] = Math.Min(Math.Min(
                    topScores[j - 1] + INDEL, bottomRow[j] + INDEL),
                    currentCharA == charB[j - 1] ? bottomRow[j - 1] + MATCH : bottomRow[j - 1] + SUB);
            }
            return topScores;
        }

        // returns a truncated version of the string "seq" with up to a "maxNumber" of characters
        // or the original string if the original string is shorter than "maxNumber"
        public String formatSequence(String seq, int maxNumber)
        {
            if (seq.Length > maxNumber)
                return seq.Substring(0, maxNumber);
            return seq;

        }

    }
}
