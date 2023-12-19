/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js,jsx,ts,tsx}"],
  theme: {
    screens: {
      'xl': '1440px',
      'md': '1024px',
    },
    extend: {
      colors: {
        'blue': '#0156FF',
        'light-blue': '#E6F0FF',
        'green': '#70C05B',
        'medium-gray': '#666666',
      },
    },
  },
  plugins: [],
};
