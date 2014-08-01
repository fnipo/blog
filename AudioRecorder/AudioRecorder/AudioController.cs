using NAudio.CoreAudioApi;
using NAudio.Wave;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AudioRecorder
{
    class AudioController
    {
        #region Variables
        private static AudioController instance;

        private MMDeviceEnumerator deviceEnumerator;
        #endregion

        #region Constructors
        private AudioController()
        {
            deviceEnumerator = new MMDeviceEnumerator();
        }
        #endregion

        #region Singleton
        public static AudioController getInstance()
        {
            if(instance == null)
            {
                instance = new AudioController();
            }
            return instance;
        }
        #endregion

        #region Public Functions
        #region GetDefaultInputDeviceNumber
        /// <summary>
        /// Returns the Device Number of the default audio input device.
        /// </summary>
        /// <returns></returns>
        public int GetDefaultInputDeviceNumber()
        {
            return GetDefaultDeviceNumber(DataFlow.Capture);
        }
        #endregion

        #region GetDefaultOutputDeviceNumber
        /// <summary>
        /// Returns the Device Number of the default audio output device.
        /// </summary>
        /// <returns></returns>
        public int GetDefaultOutputDeviceNumber()
        {
            return GetDefaultDeviceNumber(DataFlow.Render);
        }
        #endregion
        #endregion

        #region Private Functions
        #region GetDefaultDeviceNumber
        private int GetDefaultDeviceNumber(DataFlow dataFlow)
        {
            return SearchDeviceNumber(DataFlow.Capture,
                deviceEnumerator.GetDefaultAudioEndpoint(dataFlow, Role.Multimedia).FriendlyName);
        }
        #endregion

        #region SearchDeviceNumber
        private int SearchDeviceNumber(DataFlow dataFlow, string friendlyName)
        {
            int returnValue = 0;

            for (int i = 0; i < WaveIn.DeviceCount; i++)
            {
                if (WaveIn.GetCapabilities(i).ProductName.Equals(friendlyName, StringComparison.OrdinalIgnoreCase))
                {
                    returnValue = i;
                }
            }

            return returnValue;
        }
        #endregion
        #endregion
    }
}
