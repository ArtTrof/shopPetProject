import Carousel from 'nuka-carousel';
import {
  renderCenterLeftControls,
  renderCenterRightControls,
} from './Controls';

type Props = {
  wrapAround?: boolean;
  autoplay?: boolean;
  startIndex?: number;
  className?: string;
};

export const BasicDemo = ({
  wrapAround = true,
  autoplay = true,
  startIndex = 0,
}: Props) => {
  return (
    <div>
      <Carousel
        slideIndex={startIndex}
        wrapAround={wrapAround}
        autoplay={autoplay}
        autoplayInterval={2000}
        renderCenterLeftControls={renderCenterLeftControls}
        renderCenterRightControls={renderCenterRightControls}
        renderBottomCenterControls={() => null}
      >
        <img className='mx-auto' src="./image1.png" alt="" />
        <img className='mx-auto' src="./image1.png" alt="" />
        <img className='mx-auto' src="./image1.png" alt="" />
        <img className='mx-auto' src="./image1.png" alt="" />
        <img className='mx-auto' src="./image1.png" alt="" />
      </Carousel>
    </div>
  );
};