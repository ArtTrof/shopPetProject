import { Route, Routes } from 'react-router-dom';

import { Main, NotFound, SignUp, LogIn, Item, Cart } from "@/pages";
import { ContactUsWidget, Footer, Header } from '.';

export default () =>  {
  return (
    <>
      <ContactUsWidget />
      <Header />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/:itemId" element={<Item />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<LogIn />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
      <Footer />
    </>
    );
};