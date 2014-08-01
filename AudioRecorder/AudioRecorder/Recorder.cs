using NAudio.Wave;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AudioRecorder
{
    class Recorder
    {
        #region Variables
        private WaveInEvent waveIn = null;
        private WaveFileWriter writer = null;
        private bool isRecording = false;
        #endregion

        #region Properties
        public bool IsRecording
        {
            get
            {
                return this.isRecording;
            }
        }
        #endregion

        #region Constructors
        public Recorder()
        {
        }
        #endregion

        #region Pulic Funtions
        #region StartRecord
        /// <summary>
        /// Starts recording.
        /// </summary>
        public void StartRecord(string audioFileName)
        {
            waveIn = new WaveInEvent();
            waveIn.DeviceNumber = AudioController.getInstance().GetDefaultInputDeviceNumber();
            waveIn.WaveFormat = new WaveFormat(44100, 2);
            waveIn.DataAvailable += OnDataAvailable;
            waveIn.RecordingStopped += OnRecordingStopped;
            writer = new WaveFileWriter(audioFileName, waveIn.WaveFormat);
            isRecording = true;
            
            waveIn.StartRecording();
        }
        #endregion

        #region StopRecording
        /// <summary>
        /// Stops recording.
        /// </summary>
        public void StopRecording()
        {
            if (waveIn != null)
            {
                waveIn.StopRecording();
                waveIn.Dispose();
                waveIn = null;
            }
            if (writer != null)
            {
                writer.Dispose();
                writer = null;
            }

            isRecording = false;
        }
        #endregion
        #endregion

        #region Private Functions
        #region OnDataAvailable
        private void OnDataAvailable(object sender, WaveInEventArgs e)
        {
            writer.Write(e.Buffer, 0, e.BytesRecorded);
            writer.Flush();
        }
        #endregion

        #region OnRecordingStopped
        void OnRecordingStopped(object sender, StoppedEventArgs e)
        {
            Console.WriteLine("Chamou OnRecordingStopped");

            //waveIn.Dispose();
            //writer.Flush();
            //writer.Dispose();
            //isRecordingStopped = true;
        }
        #endregion        
        #endregion
    }
}
