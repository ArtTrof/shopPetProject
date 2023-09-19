import { Route, Routes } from 'react-router-dom';
import { NotFound, SignUp } from '../pages';
import Header from './Header';
import Footer from './Footer';
import ContactUsWidget from './ContactUsWidget';

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