using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace AudioRecorder
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private Player player;
        private Recorder recorder;

        public MainWindow()
        {
            InitializeComponent();

            player = new Player();
            recorder = new Recorder();
        }

        private void PlayButton_Click(object sender, RoutedEventArgs e)
        {
            player.Play(Directory.GetCurrentDirectory() + @"\" + Constants.AUDIO_FILENAME);
        }

        private void PauseButton_Click(object sender, RoutedEventArgs e)
        {
            Button pauseButton = e.Source as Button;

            if(player.IsPaused())
            {
                player.Resume();
                pauseButton.Content = Properties.Resources.pauseButtonString;
            }
            else if (player.IsStopped())
            {
                pauseButton.Content = Properties.Resources.pauseButtonString;
            }
            else
            {
                player.Pause();
                pauseButton.Content = Properties.Resources.resumeButtonString;
            }   
        }

        private void StopButton_Click(object sender, RoutedEventArgs e)
        {
            player.Stop();
        }

        private void RecordButton_Click(object sender, RoutedEventArgs e)
        {
            Button recordButton = e.Source as Button;

            if(recorder.IsRecording)
            {
                recorder.StopRecording();
                recordButton.Content = Properties.Resources.recordButtonString;
            }
            else
            {
                recorder.StartRecord(Constants.AUDIO_FILENAME);
                recordButton.Content = Properties.Resources.stopRecordingButtonString;
            }
            
        }

    }
}
