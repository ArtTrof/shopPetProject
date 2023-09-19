import React from "react";


export const NotFound: React.FC = (): React.ReactNode => {
    return (
        <div className="flex flex-col items-center justify-center h-screen">
            <img src="/not_found.svg" alt="page not found" className="h-40"/>
            <h2 className="text-2xl font-bold">Page Not Found</h2>
        </div>
    )
}

export default NotFound;