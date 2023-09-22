import { Route, Routes } from 'react-router-dom';

import { Main, NotFound, SignUp, LogIn, Item, Cart } from "@/pages";
import { ContactUsWidget, Footer, Header } from '.';

export default () =>  {
  return (
    <>
      <ContactUsWidget />
      <Header />
      <div className="flex grow justify-center">
        <div className="flex flex-col grow max-w-screen-2xl">
          <div className="flex items-center" >
            navigation
          </div>
          <div className="flex grow">
            <div className="sm:hidden lg:flex w-80">
              <Routes>
                <Route path="/" element={<div className="flex justify-center items-center">side bar</div>} />
                <Route path="/:itemId" element={<div className="flex justify-center items-center">side bar</div>} />
                <Route path="*"/>
              </Routes>
            </div>
            <div className="flex grow">
              <div className="w-full">
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
        </div>
      </div>
      <Footer />
    </>
    );
};