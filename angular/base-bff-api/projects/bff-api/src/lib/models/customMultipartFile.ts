export class CustomMultipartFile {

	/** The filename. */
	filename: string;

	/** The size. */
	size: number;

	/** The content type. */
	contentType: string;

	/** The file content. */
	content: [];

	/** The docid. */
	docId: number;

	docMgtId: string;

	compulsary: boolean;

	refNo: string;

	constructor(args: {
		filename: string,
		size: number,
		contentType: string,
		content: [],
		docId: number,
		docMgtId: string,
		compulsary: boolean,
		refNo: string,
	}
	) {
		this.filename = args.filename;
		this.size = args.size;
		this.contentType = args.contentType;
		this.content = args.content;
		this.docId = args.docId;
		this.docMgtId = args.docMgtId;
		this.compulsary = args.compulsary;
		this.refNo = args.refNo;
	}
}