using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;

namespace GeneticsLab
{
    public partial class MainForm : Form
    {
        int MaxToDisplay = 100;
        DatabaseController m_dbController;
        ResultTable m_resultTable;
        GeneSequence[] m_sequences;
        PairWiseAlign processor;

        public MainForm()
        {
            InitializeComponent();
            m_dbController = new DatabaseController();
            m_dbController.EstablishConnection("../../db1.mdb");
            statusMessage.Text = "Loading Database...";
            // Set the number of Sequences to load below.
            m_sequences = m_dbController.ReadGeneSequences(10);
            m_resultTable = new ResultTable(this.dataGridViewResults, m_sequences.Length);
            statusMessage.Text = "Loaded Database.";
            processor = new PairWiseAlign();
        }

        private void fillMatrix()
        {
            for (int y = 0; y <m_sequences.Length; ++y)
                for (int x = 0; x < m_sequences.Length; ++x)
                {
                    if (x == y)
                        m_resultTable.SetCell(x, y, "same");
                    else if (x < y)
                        m_resultTable.SetCell(x, y, (int)m_resultTable.GetCell(y, x));
                    else
                        m_resultTable.SetCell(x, y, processor.Align(m_sequences[x], m_sequences[y]));
                    //m_resultTable.SetCell(x, y, ("(" + x + ", " + y + ")"));
                }
        }

        private void processButton_Click(object sender, EventArgs e)
        {
            statusMessage.Text = "Processing...";
            Stopwatch timer = new Stopwatch();
            timer.Start();
            fillMatrix();
            timer.Stop();
            statusMessage.Text = "Done.  Time taken: " + timer.Elapsed;

        }

        private void dataGridViewResults_CellMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            GeneSequence seqA = m_sequences[e.ColumnIndex];
            GeneSequence seqB = m_sequences[e.RowIndex];

            String[] results = processor.extractSolution(seqA, seqB);
            String outputMessage = String.Format("Output Console: {0}= MATCH, {1}= SUB, {2}= INDEL",
                processor.MATCH_CHAR, processor.SUB_CHAR, processor.INDEL_CHAR);

            String outputText = String.Format("{0}\r\nGene Alignment for Cell (Row:{1}, Col:{2})\r\nA: {3}\r\n   {4}\r\nB: {5}", 
                outputMessage,
                e.RowIndex + 1, 
                e.ColumnIndex + 1,
                processor.formatSequence(results[0], MaxToDisplay),
                processor.formatSequence(results[2], MaxToDisplay),
                processor.formatSequence(results[1], MaxToDisplay));

            String sideText = String.Format("\r\n\r\nA: {0}\r\n\r\nB: {1}\r\n\r\nA: {2}\r\n\r\nB: {3}", 
                seqA.Name, 
                seqB.Name, 
                processor.formatSequence(seqA.Sequence, 15), 
                processor.formatSequence(seqB.Sequence, 15));

            sideBar.Text = sideText;
            outputConsole.Text = outputText;
        }
    }
}