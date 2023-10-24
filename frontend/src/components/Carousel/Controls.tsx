import { ControlProps } from './types';

export const renderCenterLeftControls = ({ previousDisabled, previousSlide }: ControlProps) => (
  <button
    className='border-4 border-neutral-600 rounded-lg hover:border-black'
    disabled={previousDisabled}
    onClick={previousSlide}
    aria-label="Go to previous slide"
    
  >
    <svg height="32" viewBox="0 0 48 48" width="48" xmlns="http://www.w3.org/2000/svg"><path d="M0 0h48v48h-48z" fill="none"/><path d="M40 22h-24.34l11.17-11.17-2.83-2.83-16 16 16 16 2.83-2.83-11.17-11.17h24.34v-4z"/></svg>
  </button>
);

export const renderCenterRightControls = ({ nextDisabled, nextSlide }: ControlProps) => (
  <button
    className='border-4 border-neutral-600 rounded-lg hover:border-black'
    disabled={nextDisabled}
    onClick={nextSlide}
    aria-label="Go to next slide"
  >
    <svg height="32" viewBox="0 0 48 48" width="48" xmlns="http://www.w3.org/2000/svg"><path d="M0 0h48v48h-48z" fill="none"/><path d="M24 8l-2.83 2.83 11.17 11.17h-24.34v4h24.34l-11.17 11.17 2.83 2.83 16-16z"/></svg>
  </button>
);