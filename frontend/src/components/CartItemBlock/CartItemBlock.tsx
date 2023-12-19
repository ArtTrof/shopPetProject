import React from 'react';

export default () =>  {
  const [count, setCount] = React.useState(1);

  const increment = () => {
    setCount(prevCount => prevCount + 1);
  };

  const decrement = () => {
    if (count > 1) {
      setCount(prevCount => prevCount - 1);
    }
  };
  return (
    <div className='flex py-6 border-b-4 border-gray-300'>
          <img className='mr-7' src="test-img.jpg" alt="" />
          <p className='w-64 mr-14 pt-4'>MSI MEG Trident X 10SD-1012AU Intel i7 10700K, 2070 SUPER, 32GB RAM, 1TB SSD, Windows 10 Home, Gaming Keyboard and Mouse 3 Years Warranty</p>
          <span className='font-semibold text-base mr-14 pt-4'>$4,349.00</span>
          <div className="flex items-center justify-around bg-slate-200 rounded-lg w-16 h-12 mr-14 mt-4">
            <p className="text-base font-semibold pl-2">{count}</p>
            <div className='flex flex-col'>
              <button className="pr-1 text-gray-500" onClick={increment}>▴</button>
              <button className="pr-1 text-gray-500" onClick={decrement}>▾</button>
            </div>
          </div>
          <span className='font-semibold text-base mr-14 mt-4'>$4,349.00</span>
          <div className='flex flex-col mt-4 items-center'>
            <button className='h-8 w-8'>
              <svg width="27" height="27" viewBox="0 0 27 27" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="13.4882" cy="13.4882" r="12.4882" fill="white" stroke="#CACDD8" stroke-width="2"/>
                <path d="M9.44189 9.44189L18.2093 18.2093" stroke="#A2A6B0" stroke-width="2" stroke-linecap="round"/>
                <path d="M18.209 9.44189L9.44162 18.2093" stroke="#A2A6B0" stroke-width="2" stroke-linecap="round"/>
              </svg>
            </button>
            <button className='h-8 w-8'>
              <svg width="27" height="27" viewBox="0 0 27 27" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="13.5" cy="13.5" r="12.5" fill="white" stroke="#CACDD8" stroke-width="2"/>
                <path d="M8.16323 19.9039H8.23794L11.6997 19.5884C12.0789 19.5506 12.4335 19.3836 12.7041 19.1152L20.1755 11.6439C20.4655 11.3375 20.6222 10.9287 20.6113 10.507C20.6004 10.0853 20.4228 9.68515 20.1174 9.39418L17.8428 7.11957C17.5459 6.84072 17.1569 6.68072 16.7497 6.67001C16.3426 6.65929 15.9457 6.79861 15.6346 7.06146L8.16323 14.5328C7.89489 14.8034 7.72782 15.1581 7.69004 15.5373L7.33308 18.999C7.3219 19.1206 7.33767 19.2431 7.37929 19.3579C7.4209 19.4727 7.48732 19.5769 7.57382 19.6631C7.65139 19.7401 7.74338 19.8009 7.84453 19.8422C7.94567 19.8836 8.05397 19.9045 8.16323 19.9039ZM16.6889 8.28178L18.9552 10.5481L17.2949 12.1669L15.0701 9.94208L16.6889 8.28178ZM9.30053 15.6784L13.9743 11.0379L16.2157 13.2793L11.5668 17.9281L9.07639 18.1605L9.30053 15.6784Z" fill="#A2A6B0"/>
              </svg>
            </button>
          </div>
        </div>
    );
};