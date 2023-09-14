import { ThemeProvider, createTheme } from '@mui/material/styles';

import Header from "./Header";
import Footer from "./Footer/Footer";
import ContactUsWidget from "./ContactUsWidget";
import SingUp from '../pages/SingUp/SingUp';

export default () =>  {

  const theme = createTheme({
    palette: {
      primary: {
        main: '#fff',
      },
      secondary: {
        main: '#000',
      },
    },
  });

  return (
    <ThemeProvider theme={theme}>
      <div className='min-h-screen m-0 p-0'>
        <ContactUsWidget />
        <Header />
            <SingUp/>
        <Footer />
      </div>
    </ThemeProvider>
  );
};