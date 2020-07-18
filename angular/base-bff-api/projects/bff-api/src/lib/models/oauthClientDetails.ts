
export class OauthClientDetails {
  clientId: string;
  accessTokenValidity?: number;
  additionalInformation?: string;
  authorities?: string;
  authorizedGrantTypes?: string;
  autoapprove?: string;
  clientSecret?: string;
  refreshTokenValidity?: number;
  resourceIds?: string;
  scope?: string;
  webServerRedirectUri?: string;

    constructor(args: {
      clientId: string,
      accessTokenValidity?: number,
      additionalInformation?: string,
      authorities?: string,
      authorizedGrantTypes?: string,
      autoapprove?: string,
      clientSecret?: string,
      refreshTokenValidity?: number,
      resourceIds?: string,
      scope?: string,
      webServerRedirectUri?: string
    }) {
      this.clientId = args.clientId;
      this.accessTokenValidity = args.accessTokenValidity;
      this.additionalInformation = args.additionalInformation;
      this.authorities = args.authorities;
      this.authorizedGrantTypes = args.authorizedGrantTypes;
      this.autoapprove = args.autoapprove;
      this.clientSecret = args.clientSecret;
      this.refreshTokenValidity = args.refreshTokenValidity;
      this.resourceIds = args.resourceIds;
      this.scope = args.scope;
      this.webServerRedirectUri = args.webServerRedirectUri;
   }
}
