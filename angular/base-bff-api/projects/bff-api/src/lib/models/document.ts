import { CustomMultipartFile } from './customMultipartFile';

export class Document{
    
    id: string;

	/** The docid. */
	docid: string;

	/** The files id. */
	filesId: string;

	/** The filename. */
	filename: string;

	/** The length. */
	length: number;

	/** The content type. */
	contentType: string ;

	/** The upload date. */
	uploadDate: Date ;

	/** The file content. */
	content: any[] ;
	
	customMultipartFile: CustomMultipartFile;
}