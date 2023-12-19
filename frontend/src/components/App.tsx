import { Route, Routes } from 'react-router-dom';

import { Main, NotFound, SignUp, LogIn, Item, Cart } from "@/pages";
import { ContactUsWidget, Footer, Header } from '.';

export default () =>  {
  return (
    <>
      <ContactUsWidget />
      <Header />
      <div className="flex grow justify-center container mx-auto">
        <div className="flex flex-col grow max-w-screen-2xl">
          <div className="flex items-center" >
            navigation
          </div>
          <div className="flex grow justify-start items-start">
              <Routes>
                <Route path="/" element={<Main />} />
                <Route path="/:itemId" element={<Item />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/login" element={<LogIn />} />
                <Route path="/cart" element={<Cart />} />
                <Route path="*" element={<NotFound />} />
              </Routes>
          </div>
        </div>
      </div>
      <Footer />
    </>
    );
};