import { ERRORS_EN } from './errors-mapping-en';
import { ERRORS_MS } from './errors-mapping-ms';
import { ERRORS_BN } from './errors-mapping-bn';

/**
 * Use in SimpleMessagesProviderService.
 * Note: Do use Locale 'en-US' / 'de-DE' as 'en_US' / 'de_DE'. 
 */
export const ERRORS = {
en : ERRORS_EN,
en_US : ERRORS_EN,
ms : ERRORS_MS,
bn : ERRORS_BN
};