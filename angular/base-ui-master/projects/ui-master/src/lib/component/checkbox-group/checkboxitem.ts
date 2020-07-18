export class CheckboxItem {
	id: string;
	label: string;
	value: string;
	checked: boolean;

	constructor( id: any, label: any, value?: any, checked?: boolean) {
		this.id = id;
		this.label = label; // printed on screen
		this.value = value ? value : label; // value if not set - default as label
		this.checked = checked ? checked : false;
	}    
}