using NAudio.Wave;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AudioRecorder
{
    class Player
    {
        #region Variables
        private WaveOut waveOut = null;
        #endregion

        #region Constructors
        public Player()
        {
        }
        #endregion

        #region Pulic Funtions
        #region Play
        /// <summary>
        /// Starts playback.
        /// </summary>
        public void Play(string audioFilePath)
        {
            try
            {
                MemoryStream audioData = new MemoryStream(File.ReadAllBytes(audioFilePath));
                WaveFormat waveFormat = new WaveFormat(44100, 2);
                RawSourceWaveStream waveStream = new RawSourceWaveStream(audioData, waveFormat);

                waveOut = new WaveOut();
                waveOut.DeviceNumber = AudioController.getInstance().GetDefaultOutputDeviceNumber();
                waveOut.Init(waveStream);
                waveOut.Play();
            }
            catch (FileNotFoundException e)
            {
                // TODO
            }
            catch (IOException e)
            {
                // TODO
            }
        }
        #endregion

        #region Stop
        /// <summary>
        /// Stops playback.
        /// </summary>
        public void Stop()
        {
            if (waveOut != null)
            {
                waveOut.Stop();
                waveOut.Dispose();
            }
        }
        #endregion

        #region Pause
        /// <summary>
        /// Stops playback.
        /// </summary>
        public void Pause()
        {
            if(waveOut != null)
            {
                waveOut.Pause();
            }
        }
        #endregion

        #region Resume
        /// <summary>
        /// Resumes playback.
        /// </summary>
        public void Resume()
        {
            waveOut.Resume();
        }
        #endregion

        #region IsPaused
        /// <summary>
        /// Returns true if playback is paused.
        /// </summary>
        public bool IsPaused()
        {
            if(waveOut != null)
            {
                return (waveOut.PlaybackState == PlaybackState.Paused);
            }

            return false;
        }
        #endregion

        #region IsStopped
        /// <summary>
        /// Returns true if playback is stopped.
        /// </summary>
        public bool IsStopped()
        {
            if(waveOut != null)
            {
                return (waveOut.PlaybackState == PlaybackState.Stopped);
            }

            return true;
        }
        #endregion
        #endregion
    }

}
