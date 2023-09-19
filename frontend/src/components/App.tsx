import Header from "./Header";
import Footer from "./Footer/Footer";
import ContactUsWidget from "./ContactUsWidget";
        
import { Route, Routes } from 'react-router-dom';
import { NotFound } from '@pages/NotFound';
import { SignUp } from '@pages/SignUp';

export default () =>  {
  return (
    <>
      <ContactUsWidget />
      <Header />
      <Routes>
        <Route path="signup" element={<SignUp />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
      <Footer />
    </>
    );
};