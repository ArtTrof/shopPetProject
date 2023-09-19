import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import tsconfigPaths from 'vite-tsconfig-paths';

// https://vitejs.dev/config/
export default defineConfig({
  root: './',
  publicDir: './public',
  server: {
    host: true,
    port: 8080,
    open: true,
  },
  preview: {
    host: true,
    port: 8080,
    open: true,
  },
  plugins: [react(), tsconfigPaths()],
})
