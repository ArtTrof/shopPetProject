import React from "react";
import { useNavigate } from 'react-router-dom';

import Grid2 from "@mui/material/Unstable_Grid2";

import { isValidEmail } from "@/utils";
import { customerService } from "@/services";


const MIN_PASSWD_LENGTH = 8;

export const LogIn: React.FC = (): React.ReactNode => {
    const navigate = useNavigate();


    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");

    const onRegisterSubmit = async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>): Promise<void> => {
      if (!isValidEmail(email)) {
        showError('Invalid email')
        return;
      }
      if (password.length < MIN_PASSWD_LENGTH) {
        showError('Password is too short')
        return;
      }

      customerService.login(email, password)
        .then(() => navigate('/'))
        .catch(() => showError('Failed to login!'));
    }

    const showError = (message: string): void => {
      console.error(message);
    }

    return (
      <div className="flex justify-center">
        <div className="bg-[#E6EAFF] w-[30rem] p-5 m-5">
          <h1 className="text-xl font-bold mb-3">Registered Customers</h1>
          <h5 className="mb-6">If you have an account, sign in with your email address.</h5>
          <p className="font-bold mb-2">Email <span className="text-red-500" >*</span>  </p>
          <input placeholder="Email" type="email" className="p-3 rounded border-2 border-[#A2A6B0] focus:outline-[#0156FF] mb-6" onInput={e => setEmail((e.target as HTMLInputElement).value)}  />
          <p className="font-bold mb-2">Password <span className="text-red-500" >*</span>  </p>
          <input placeholder="Password" type="password" className="p-3 rounded border-2 border-[#A2A6B0] focus:outline-[#0156FF] mb-6" onInput={e => setPassword((e.target as HTMLInputElement).value)}  />
          <div>
            <button className="bg-[#0156FF] w-max !rounded-full px-10 py-3 mb-6 text-white" onClick={(e) => onRegisterSubmit(e)}>Sign In</button>
            <span className="text-[#0156FF] ml-5" >Forgot password?</span>
          </div>
        </div>
        <div className="bg-[#E6EAFF] w-[30rem] p-5  m-5">
          <h1 className="text-xl font-bold mb-3">New customer?</h1>
          <h5 className="mb-6">Creating an account has many benefits:</h5>
          <ul className="list-disc ml-4 mb-6">
              <li>Checkout faster</li>
              <li>Save multiple shipping addresses</li>
              <li>View and track orders and more</li>
          </ul>
          <button className="bg-[#0156FF] w-max !rounded-full px-10 py-3 mb-7 text-white" onClick={(e) => navigate('/signup')}>Create An Account</button>
        </div>
      </div>
    );
}

export default LogIn;