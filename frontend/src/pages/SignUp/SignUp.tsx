import * as React from "react";

import { Input } from "@/components";

import { customerService } from "@/services";

export default () => {
    const [firstName, setFirstName] = React.useState("");
    const [lastName, setLastName] = React.useState("");
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [rePassword, setRePassword] = React.useState("");
    const [phone, setPhone] = React.useState("");
    const [reqRes, setReqRes] = React.useState("");

    const onRegisterSubmit = async (e: React.FormEvent<HTMLButtonElement>) => {
        e.preventDefault();
        customerService.signup(email, password, firstName, lastName, phone)
        .then(() => setReqRes("Successfully registered!"))
        .catch(() => setReqRes("Failed to register!"));
    }

    return (
        <div className="flex justify-center w-full mb-5">
            <div className="bg-light-blue p-11">
                <h4 className="text-center !text-2xl !font-bold mb-6">Registration</h4>
                <h4 className="text-center !text-2xl !font-bold mb-2">{reqRes}</h4>
                <div className="flex">
                    <div className="p-3 w-1/2">
                        <Input label="First name" placeholder="First name" required value={firstName} onChange={e => setFirstName((e.target as HTMLInputElement).value)}/>
                        <Input label="Last name" placeholder="Last name" required value={firstName} onChange={e => setLastName((e.target as HTMLInputElement).value)}/>
                        <Input label="Phone number" placeholder="Last name" type="tel" required value={firstName} onChange={e => setPhone((e.target as HTMLInputElement).value)}/>
                        <button className="bg-blue rounded-full px-10 py-3 text-white float-right" onClick={e => onRegisterSubmit(e)}>Sign In</button>
                    </div>
                    <div className="p-3 w-1/2">
                        <Input label="Email" placeholder="Email" type="email" required value={email} onChange={e => setEmail((e.target as HTMLInputElement).value)}/>
                        <Input label="Password" placeholder="Password" type="password" required value={password} onChange={e => setPassword((e.target as HTMLInputElement).value)}/>
                        <Input
                            label="Repeat password"
                            placeholder="Repeat password"
                            type="password"
                            required
                            error={password !== rePassword}
                            value={rePassword}
                            onChange={e => setRePassword((e.target as HTMLInputElement).value)}/>
                        <div className="text-blue mt-[13px]" >Forgot password?</div>
                    </div>
                </div>
            </div>
        </div>
    )
};