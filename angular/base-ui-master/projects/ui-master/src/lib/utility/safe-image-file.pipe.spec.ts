import { SafeImageFilePipe } from './safe-image-file.pipe';

describe('SafeImageFilePipe', () => {
  it('create an instance', () => {
    const pipe = new SafeImageFilePipe(null);
    expect(pipe).toBeTruthy();
  });
});
