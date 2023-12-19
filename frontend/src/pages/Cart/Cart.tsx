import CartItemBlock from '@/components/CartItemBlock/CartItemBlock';
import React from 'react';


const Cart: React.FC = (): React.ReactNode => {

  

  return (
    <div className='flex w-full mb-14'>

      <div className='flex flex-col w-2/3'>
        <h1 className='text-2xl font-bold mt-9 mb-7'>Shopping Cart</h1>
        <div className='flex items-center pb-3 border-b-4 border-gray-300'>
          <p className='font-semibold text-base mr-96'>Item</p>
          <p className='font-semibold text-base ml-20 mr-24'>Price</p>
          <p className='font-semibold text-base mr-20'>Qty</p>
          <p className='font-semibold text-base'>Subtotal</p>
        </div>
        <CartItemBlock />
        <div className='flex items-center justify-center'>
          <button className='h-12 mt-3 mx-8 px-8 rounded-3xl border-4 text-gray-500 font-semibold'>
            Continue Shopping
          </button>
          <button className='h-12 mt-3 mx-8 px-8 rounded-3xl bg-black text-white font-semibold'>
            Clear Shopping Cart
          </button>
        </div>
      </div>
      <div className='w-1/3 bg-slate-200 mt-20 mx-6 p-6 rounded-xl'>
        <div className='border-b-4 border-gray-300 pb-6 mb-3'>
          <h2 className='text-2xl font-semibold my-5'>Summary</h2>
          <p className='text-xl text-base my-5'>Estimate Shipping and Tax</p>
          <span className='my-5 text-gray-500'>Enter your destination to get a shipping <br/> estimate.</span>
          <p className='text-xl my-5'>Apply Discount Code</p>
        </div>
        <div>
          <div className='flex justify-between my-4'>
            <span className='font-semibold'>Subtotal</span>
            <span className='font-semibold'>$4,349.00</span>
          </div>
          <div className='flex justify-between my-5'>
            <span className='font-semibold'>Shipping</span>
            <span className='font-semibold'>$21.00</span>
          </div>
          <span className='text-gray-500'>(Standard Rate - Price may vary depending on the item/destination. TECS Staff will contact you.)</span>
          <div className='flex justify-between my-5'>
            <span className='font-semibold'>Tax</span>
            <span className='font-semibold'>$1.91</span>
          </div>
          <div className='flex justify-between my-5'>
            <span className='font-semibold'>GST (10%)</span>
            <span className='font-semibold'>$1.91</span>
          </div>
          <div className='flex justify-between my-5'>
            <span className='font-semibold'>Order Total</span>
            <span className='font-semibold'>$4,349.00</span>
          </div>
          <div className='flex flex-col items-center justify-center'>
            <button className='w-full h-12 mx-8 rounded-3xl bg-blue text-white font-semibold'>
              Proceed to Checkout
            </button>
            <button className='w-full h-12 flex items-center justify-center mt-3 mx-8 rounded-3xl bg-yellow-500 text-indigo-950 font-semibold'>
              <span className='mr-3'>Check out with</span>
              <svg width="72" height="18" viewBox="0 0 72 18" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M55.6932 5H52.0409C51.8261 5 51.6113 5.21053 51.5039 5.42105L50 14.6842C50 14.8947 50.1074 15 50.3223 15H52.2558C52.4706 15 52.578 14.8947 52.578 14.6842L53.0077 12.0526C53.0077 11.8421 53.2225 11.6316 53.5448 11.6316H54.7264C57.197 11.6316 58.5935 10.4737 58.9157 8.15789C59.1306 7.21053 58.9157 6.36842 58.486 5.84211C57.8415 5.31579 56.8748 5 55.6932 5ZM56.1228 8.47368C55.908 9.73684 54.9412 9.73684 53.9745 9.73684H53.33L53.7596 7.31579C53.7596 7.21053 53.8671 7.10526 54.0819 7.10526H54.2967C54.9412 7.10526 55.5858 7.10526 55.908 7.52631C56.1228 7.63158 56.1228 7.94737 56.1228 8.47368Z" fill="#139AD6"/>
                <path d="M27.6572 4.6001H23.7715C23.5429 4.6001 23.3144 4.82867 23.2001 5.05724L21.6001 15.1144C21.6001 15.3429 21.7144 15.4572 21.943 15.4572H23.7715C24.0001 15.4572 24.2287 15.2286 24.3429 15.0001L24.8001 12.2572C24.8001 12.0286 25.0287 11.8001 25.3715 11.8001H26.6287C29.2572 11.8001 30.7429 10.5429 31.0858 8.02866C31.3144 7.00009 31.0858 6.08581 30.6286 5.51438C29.9429 4.94296 29.0286 4.6001 27.6572 4.6001ZM28.1144 8.37152C27.8858 9.74294 26.8572 9.74294 25.8287 9.74294H25.2572L25.7144 7.11438C25.7144 7.00009 25.8287 6.88581 26.0572 6.88581H26.2858C26.9715 6.88581 27.6572 6.8858 28.0001 7.34295C28.1144 7.45723 28.2287 7.80009 28.1144 8.37152Z" fill="#263B80"/>
                <path d="M39.4287 8.25738H37.6001C37.4858 8.25738 37.2573 8.37166 37.2573 8.48595L37.143 9.05738L37.0287 8.82881C36.5716 8.25738 35.7716 8.02881 34.8573 8.02881C32.8001 8.02881 30.9716 9.62881 30.6287 11.8002C30.4002 12.9431 30.743 13.9717 31.3144 14.6574C31.8859 15.3431 32.6859 15.5716 33.7144 15.5716C35.4287 15.5716 36.343 14.5431 36.343 14.5431L36.2287 15.1145C36.2287 15.3431 36.343 15.4574 36.5716 15.4574H38.2858C38.5144 15.4574 38.743 15.2288 38.8573 15.0002L39.8858 8.60024C39.7716 8.48595 39.543 8.25738 39.4287 8.25738ZM36.8001 11.9145C36.5716 12.9431 35.7716 13.7431 34.6287 13.7431C34.0573 13.7431 33.6001 13.5145 33.3716 13.2859C33.143 12.9431 33.0287 12.4859 33.0287 11.9145C33.143 10.8859 34.0573 10.0859 35.0859 10.0859C35.6573 10.0859 36.0001 10.3145 36.343 10.5431C36.6858 10.8859 36.8001 11.4574 36.8001 11.9145Z" fill="#263B80"/>
                <path d="M67.51 8.24243H65.5498C65.4273 8.24243 65.1823 8.36364 65.1823 8.48485L65.0598 9.09091L64.9373 8.84849C64.4472 8.24243 63.5897 8 62.6096 8C60.4044 8 58.4443 9.69697 58.0767 12C57.8317 13.2121 58.1993 14.303 58.8118 15.0303C59.4243 15.7576 60.2819 16 61.3845 16C63.2221 16 64.2022 14.9091 64.2022 14.9091L64.0797 15.5151C64.0797 15.7576 64.2022 15.8788 64.4472 15.8788H66.2849C66.5299 15.8788 66.7749 15.6364 66.8974 15.3939L68 8.60606C67.8775 8.48485 67.755 8.24243 67.51 8.24243ZM64.6922 12.1212C64.4472 13.2121 63.5897 14.0606 62.3646 14.0606C61.752 14.0606 61.262 13.8182 61.017 13.5758C60.7719 13.2121 60.6494 12.7273 60.6494 12.1212C60.772 11.0303 61.752 10.1818 62.8546 10.1818C63.4672 10.1818 63.8347 10.4242 64.2022 10.6667C64.6922 11.0303 64.8148 11.6364 64.6922 12.1212Z" fill="#139AD6"/>
                <path d="M49.7137 8H47.7899C47.5636 8 47.4504 8.11494 47.3372 8.22988L44.8476 12.1379L43.716 8.45977C43.6028 8.22988 43.4896 8.11494 43.1501 8.11494H41.3395C41.1132 8.11494 41 8.34483 41 8.57471L43.037 14.6667L41.1132 17.4253C41 17.6552 41.1132 18 41.3395 18H43.1501C43.3765 18 43.4896 17.8851 43.6028 17.7701L49.8269 8.68966C50.1664 8.34483 49.94 8 49.7137 8Z" fill="#263B80"/>
                <path d="M69.5556 5.27587L68 15.6541C68 15.8847 68.1111 16 68.3333 16H69.8889C70.1111 16 70.3333 15.7694 70.4444 15.5387L72 5.39118C72 5.16055 71.8889 5.04524 71.6667 5.04524H69.8889C69.7778 4.92992 69.6667 5.04524 69.5556 5.27587Z" fill="#139AD6"/>
                <path d="M13.0638 1.35211C12.2686 0.450703 10.7918 0 8.74706 0H3.06715C2.72636 0 2.38556 0.338026 2.27196 0.676055L0 15.4366C0 15.7746 0.227196 16 0.454393 16H3.97594L4.88472 10.4789V10.7042C4.99832 10.3662 5.33911 10.0282 5.67991 10.0282H7.38388C10.6782 10.0282 13.1774 8.67605 13.9726 4.95775C13.9726 4.84507 13.9726 4.73239 13.9726 4.61972C13.859 4.61972 13.859 4.61972 13.9726 4.61972C14.0862 3.15493 13.859 2.25352 13.0638 1.35211Z" fill="#263B80"/>
                <path d="M13.712 4C13.712 4.11765 13.712 4.2353 13.712 4.35294C12.9566 8.35294 10.5826 9.64706 7.45314 9.64706H5.83448C5.51075 9.64706 5.18702 10 5.07911 10.3529L4 17.5294C4 17.7647 4.10791 18 4.43164 18H7.23732C7.56105 18 7.88479 17.7647 7.88479 17.4118V17.2941L8.42434 13.6471V13.4118C8.42434 13.0588 8.74807 12.8235 9.07181 12.8235H9.50345C12.2012 12.8235 14.3594 11.6471 14.899 8.11765C15.1148 6.70588 15.0069 5.41176 14.3594 4.58824C14.2515 4.35294 14.0357 4.11765 13.712 4Z" fill="#139AD6"/>
                <path d="M13 4.35C12.8889 4.35 12.7778 4.23333 12.6667 4.23333C12.5556 4.23333 12.4444 4.23333 12.3333 4.11666C11.8889 4 11.4444 4 10.8889 4H6.55556C6.44444 4 6.33333 4 6.22222 4.11666C6 4.23333 5.88889 4.46666 5.88889 4.7L5 10.7667V11C5.11111 10.65 5.44444 10.3 5.77778 10.3H7.44444C10.6667 10.3 13.1111 8.9 13.8889 5.05C13.8889 4.93333 13.8889 4.81666 14 4.7C13.7778 4.58333 13.6667 4.46667 13.4444 4.46667C13.1111 4.35 13.1111 4.35 13 4.35Z" fill="#232C65"/>
              </svg>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Cart;